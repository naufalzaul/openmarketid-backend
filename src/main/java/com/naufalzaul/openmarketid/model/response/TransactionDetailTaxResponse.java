package com.naufalzaul.openmarketid.model.response;

public record TransactionDetailTaxResponse(
        String taxId,
        String taxDescription,
        Double taxPercentage
) {}
