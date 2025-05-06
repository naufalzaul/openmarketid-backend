package com.naufalzaul.openmarketid.model.response;

import java.util.List;

public record TaxResponse(
        String id,
        String description,
        Double taxPercentage,
        Boolean isActive,
        List<ProductTaxResponse> productTaxes
) {}
