package com.naufalzaul.openmarketid.service.impl;

import com.naufalzaul.openmarketid.entity.User;
import com.naufalzaul.openmarketid.model.request.user.AuthRequest;
import com.naufalzaul.openmarketid.model.response.AuthResponse;
import com.naufalzaul.openmarketid.security.JwtTokenProvider;
import com.naufalzaul.openmarketid.service.AuthService;
import com.naufalzaul.openmarketid.service.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final AuthMapper authMapper;

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );


        User user = (User) authentication.getPrincipal();

        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getAuthorities());

        return authMapper.fromAuth(token);
    }
}
