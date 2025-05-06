package com.naufalzaul.openmarketid.model.request.payment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    private String transactionId;
                                   
    private Double totalPayment;

}
