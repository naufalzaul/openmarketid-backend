package com.naufalzaul.openmarketid.model.response;

import lombok.Builder;

import java.util.List;

@Builder
public record TaxResponse(
        String id,
        String description,
        Double percentage,
        Boolean isActive
//        List<ProductTaxResponse> productTaxes
) {}
