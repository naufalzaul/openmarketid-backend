package com.naufalzaul.openmarketid.controller;

import com.naufalzaul.openmarketid.constant.APIBash;
import com.naufalzaul.openmarketid.model.request.user.AuthRequest;
import com.naufalzaul.openmarketid.model.response.AuthResponse;
import com.naufalzaul.openmarketid.model.response.CommonResponse;
import com.naufalzaul.openmarketid.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIBash.AUTH)
public class AuthController {

    private final AuthService authService;

    @PostMapping(APIBash.LOGIN)
    public ResponseEntity<CommonResponse<AuthResponse>> login(
            @RequestBody AuthRequest authRequest) {

        AuthResponse authResponse = authService.login(authRequest);

        CommonResponse<AuthResponse> response = new CommonResponse<>(
                HttpStatus.CREATED.value(),
                "Successfully login",
                authResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
