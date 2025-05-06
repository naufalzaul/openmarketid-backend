package com.naufalzaul.openmarketid.model.request.transaction;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionFilterRequest {
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;

    private String startDate;
    private String endDate;

    private String customerName;
    private String paymentMethod;
    private String transactionStatus;
    private String createdBy;
}
