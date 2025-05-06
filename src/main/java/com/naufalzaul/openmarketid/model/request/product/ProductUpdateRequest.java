package com.naufalzaul.openmarketid.model.request.product;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductUpdateRequest {
    @NotNull(message = "ID is required")
    private String id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Price is required")
    private double price;

    @NotNull(message = "Available quantity is required")
    private Integer availableQuantity;
}
