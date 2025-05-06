package com.naufalzaul.openmarketid.model.request.product;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateRequest {

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Price is required")
    private double price;

    @NotNull(message = "Available quantity is required")
    private Integer availableQuantity;
    
    private List<String> taxIds;
}
