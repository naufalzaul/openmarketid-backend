package com.naufalzaul.openmarketid.model.response;

import lombok.Builder;

@Builder
public record CustomerResponse(
        String id,
        String name,
        String email,
        String birthdate,
        String birthplace,
        String created_by,
        String updated_by
) {}
