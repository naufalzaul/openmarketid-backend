package com.naufalzaul.openmarketid.model.response;

import java.util.List;

public record ProductResponse(
        String id,
        String name,
        Double price,
        Integer availableQuantity,
        List<ProductTaxResponse> productTaxes
) {}
