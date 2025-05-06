package com.naufalzaul.openmarketid.service.mapper;

import com.naufalzaul.openmarketid.model.response.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthMapper {
    public AuthResponse fromAuth(String token) {
        return new AuthResponse(token);
    }
}
