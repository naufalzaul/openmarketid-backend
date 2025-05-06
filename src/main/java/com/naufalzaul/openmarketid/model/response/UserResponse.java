package com.naufalzaul.openmarketid.model.response;

public record UserResponse(
        String id,
        String name,
        String email,
        String role
) {}
