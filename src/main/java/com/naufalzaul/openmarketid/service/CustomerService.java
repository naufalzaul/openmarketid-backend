package com.naufalzaul.openmarketid.service;

import com.naufalzaul.openmarketid.entity.Customer;
import com.naufalzaul.openmarketid.model.request.customer.CustomerCreateRequest;
import com.naufalzaul.openmarketid.model.request.customer.CustomerUpdateRequest;
import com.naufalzaul.openmarketid.model.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerCreateRequest request);
    List<CustomerResponse> findAllCustomers();
    Customer findCustomerById(String id);
    CustomerResponse findCustomerByUserEmail();
    CustomerResponse updateCustomer(CustomerUpdateRequest request);
    void deleteCustomer(String id);
}
