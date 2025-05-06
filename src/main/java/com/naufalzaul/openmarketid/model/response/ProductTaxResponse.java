package com.naufalzaul.openmarketid.model.response;

import lombok.Builder;

@Builder
public record ProductTaxResponse(
        String id,
        String productId,
        String taxId,
        Boolean isActive
        //        String productName,
        //        Double productPrice,
        //        Integer productAvailableQuantity,
        //        String taxDescription,
        //        Double taxPercentage,
        //        Boolean isActive
) {}
