package com.naufalzaul.openmarketid.model.response;

import java.util.List;

public record TransactionDetailResponse(
//        String id,
        String productId,
        String productName,
        Double productPrice,
        Integer totalQuantity,
        Double taxAmount,
        List<TransactionDetailTaxResponse> productTaxes
) {}
