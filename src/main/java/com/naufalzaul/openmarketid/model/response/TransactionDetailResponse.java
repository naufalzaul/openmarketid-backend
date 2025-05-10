package com.naufalzaul.openmarketid.model.response;

import java.util.List;

public record TransactionDetailResponse(
        String productId,
        String productName,
        Double productPrice,
        Integer totalQuantity,
        Double taxAmount,
        List<DetailTaxResponse> productTaxes
) {
    public record DetailTaxResponse(
            String taxId,
            String taxDescription,
            Double taxPercentage
    ) {}
}


