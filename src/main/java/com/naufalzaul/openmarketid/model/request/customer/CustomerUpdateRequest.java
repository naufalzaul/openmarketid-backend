package com.naufalzaul.openmarketid.model.request.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerUpdateRequest {

    @NotNull(message = "ID is required")
    private String id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Birth Date is required")
    private String birthDate;

    @NotNull(message = "Birth Place is required")
    private String birthPlace;

}


