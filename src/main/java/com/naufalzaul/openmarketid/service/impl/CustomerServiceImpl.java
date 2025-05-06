package com.naufalzaul.openmarketid.service.impl;

import com.naufalzaul.openmarketid.constant.UserRole;
import com.naufalzaul.openmarketid.entity.Customer;
import com.naufalzaul.openmarketid.entity.User;
import com.naufalzaul.openmarketid.exception.DataNotFoundException;
import com.naufalzaul.openmarketid.model.request.customer.CustomerCreateRequest;
import com.naufalzaul.openmarketid.model.request.customer.CustomerUpdateRequest;
import com.naufalzaul.openmarketid.model.request.user.UserCreateRequest;
import com.naufalzaul.openmarketid.model.request.user.UserUpdateRequest;
import com.naufalzaul.openmarketid.model.response.CustomerResponse;
import com.naufalzaul.openmarketid.model.response.UserResponse;
import com.naufalzaul.openmarketid.repository.CustomerRepository;
import com.naufalzaul.openmarketid.service.CustomerService;
import com.naufalzaul.openmarketid.service.UserService;
import com.naufalzaul.openmarketid.service.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final UserService userService;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponse createCustomer(CustomerCreateRequest request) {

        UserResponse userByEmail = userService.findUserByEmail();

        User createUser = userService.createUser(
                UserCreateRequest.builder()
                        .name(request.getName())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .role(UserRole.findByRole(request.getRole()))
                        .build()
        );

        Customer customer = Customer.builder()
                .name(request.getName())
                .birthDate(LocalDate.parse(request.getBirthDate()))
                .birthPlace(request.getBirthPlace())
                .createdBy(userByEmail.name())
                .createdAt(LocalDateTime.now())
                .user(createUser)
                .build();

        customerRepository.save(customer);

        return customerMapper.fromCustomer(customer);
    }

    @Override
    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .stream().map(customerMapper::fromCustomer).toList();
    }

    @Override
    public Customer findCustomerById(String id) {
        return customerRepository
                .findById(id)
                .orElseThrow(() -> new DataNotFoundException(
                        format("Customer not found with id %s", id)));
    }

    @Override
    public CustomerResponse findCustomerByUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Customer customer = customerRepository
                .findByUserEmail(email)
                .orElseThrow(() -> new DataNotFoundException(
                        format("Customer not found with user email %s", email)));

        return customerMapper.fromCustomer(customer);
    }

    @Override
    public CustomerResponse updateCustomer(CustomerUpdateRequest request) {

        UserResponse findUpdater = userService.findUserByEmail();

        Customer customerUpdate = findCustomerById(request.getId());

        User updateUser = userService.updateUser(
                UserUpdateRequest.builder()
                        .id(customerUpdate.getUser().getId())
                        .name(request.getName())
                        .email(request.getEmail())
                        .build()
        );

        customerUpdate.setName(request.getName());
        customerUpdate.setBirthDate(LocalDate.parse(request.getBirthDate()));
        customerUpdate.setBirthPlace(request.getBirthPlace());
        customerUpdate.setUpdatedBy(findUpdater.name());
        customerUpdate.setUpdatedAt(LocalDateTime.now());
        customerUpdate.setUser(updateUser);

        customerRepository.save(customerUpdate);
        return customerMapper.fromCustomer(customerUpdate);
    }

    @Override
    public void deleteCustomer(String id) {
        Customer customerById = findCustomerById(id);
        customerRepository.deleteById(id);
        userService.deleteUser(customerById.getUser().getId());
    }
}
