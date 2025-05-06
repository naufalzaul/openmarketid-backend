package com.naufalzaul.openmarketid.controller;

import com.naufalzaul.openmarketid.constant.APIBash;
import com.naufalzaul.openmarketid.model.request.customer.CustomerCreateRequest;
import com.naufalzaul.openmarketid.model.request.customer.CustomerUpdateRequest;
import com.naufalzaul.openmarketid.model.response.CommonResponse;
import com.naufalzaul.openmarketid.model.response.CustomerResponse;
import com.naufalzaul.openmarketid.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIBash.CUSTOMER)
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse<CustomerResponse>> createCustomer(
            @RequestBody CustomerCreateRequest request) {

        CustomerResponse customerResponse = customerService.createCustomer(request);

        CommonResponse<CustomerResponse> response = new CommonResponse<>(
                HttpStatus.CREATED.value(),
                "Customer successfully created",
                customerResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> findAllCustomers() {

        List<CustomerResponse> customerResponse = customerService.findAllCustomers();

        CommonResponse<List<CustomerResponse>> response = new CommonResponse<>(
                HttpStatus.OK.value(),
                "All customers found",
                customerResponse
        );

        return ResponseEntity.ok(response);
    }
    @GetMapping(APIBash.FIND_BY_AUTH_TOKEN)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<CommonResponse<CustomerResponse>> findCustomerByUserEmail() {

        CustomerResponse customerResponse = customerService.findCustomerByUserEmail();

        CommonResponse<CustomerResponse> response = new CommonResponse<>(
                HttpStatus.OK.value(),
                "Customer found",
                customerResponse
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(
            @RequestBody CustomerUpdateRequest request
    ) {

        CustomerResponse customerResponse = customerService.updateCustomer(request);

        CommonResponse<CustomerResponse> response = new CommonResponse<>(
                HttpStatus.OK.value(),
                "Customer successfully updated",
                customerResponse
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(APIBash.GET_BY_ID)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<CommonResponse<CustomerResponse>> deleteCustomer(@PathVariable String id) {

        customerService.deleteCustomer(id);

        CommonResponse<CustomerResponse> response = new CommonResponse<>(
                HttpStatus.OK.value(),
                "Customer successfully deleted",
                null
        );

        return ResponseEntity.ok(response);
    }
}
