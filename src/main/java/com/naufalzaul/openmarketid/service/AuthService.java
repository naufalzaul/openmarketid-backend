package com.naufalzaul.openmarketid.service;


import com.naufalzaul.openmarketid.model.request.user.AuthRequest;
import com.naufalzaul.openmarketid.model.response.AuthResponse;

public interface AuthService {
    AuthResponse login (AuthRequest authRequest);
}
