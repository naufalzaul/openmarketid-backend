package com.naufalzaul.openmarketid.model.request.transaction;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetailRequest {

    @NotNull(message = "Product is required")
    private String productId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

}
