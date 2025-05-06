package com.naufalzaul.openmarketid.model.request.product_tax;

import com.naufalzaul.openmarketid.entity.Product;
import com.naufalzaul.openmarketid.entity.Tax;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductTaxRequest {
    private String id;

    @NotNull(message = "Product ID is required")
    private Product product;

    @NotNull(message = "Tax ID is required")
    private Tax tax;

    private Boolean isActive;
}
