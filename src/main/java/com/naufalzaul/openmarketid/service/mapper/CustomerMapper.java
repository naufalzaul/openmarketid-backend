package com.naufalzaul.openmarketid.service.mapper;

import com.naufalzaul.openmarketid.entity.Customer;
import com.naufalzaul.openmarketid.model.response.CustomerResponse;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getUser().getEmail(),
                customer.getBirthDate().toString(),
                customer.getBirthPlace(),
                customer.getCreatedBy(),
                customer.getUpdatedBy()
        );
    }
}
