package com.naufalzaul.openmarketid.model.request.transaction;

import com.naufalzaul.openmarketid.constant.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {

    @NotNull(message = "Customer ID is required")
    private String customerId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @NotNull(message = "List product is required")
    private List<TransactionDetailRequest> products;
}
