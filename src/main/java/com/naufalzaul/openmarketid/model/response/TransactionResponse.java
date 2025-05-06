package com.naufalzaul.openmarketid.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.naufalzaul.openmarketid.constant.PaymentMethod;
import com.naufalzaul.openmarketid.constant.TransactionStatus;

import java.time.LocalDateTime;
import java.util.List;

public record TransactionResponse(
        String id,
        CustomerResponse customer,
        double netAmount,
        double totalTax,
        double totalAmount,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime transactionDate,
        TransactionStatus transactionStatus,
        List<TransactionDetailResponse> transactionDetails,
        PaymentMethod paymentMethod,
        LocalDateTime paymentDate
) {}
