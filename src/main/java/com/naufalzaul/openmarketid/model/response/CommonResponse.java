package com.naufalzaul.openmarketid.model.response;

public record CommonResponse<T>(
        Integer status,
        String message,
        T data
) {}