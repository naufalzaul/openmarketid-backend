package com.naufalzaul.openmarketid.model.response;

import lombok.Builder;

@Builder
public record ProductTaxResponse(
        String id,
        String description,
        Double percentage,
        Boolean isActive
) {}
