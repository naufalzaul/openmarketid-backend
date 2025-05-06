package com.naufalzaul.openmarketid.controller;

import com.naufalzaul.openmarketid.constant.APIBash;
import com.naufalzaul.openmarketid.model.response.CommonResponse;
import com.naufalzaul.openmarketid.model.response.UserResponse;
import com.naufalzaul.openmarketid.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIBash.USER)
//@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<UserResponse>>> findAllUsers() {

        List<UserResponse> allUsers = userService.findAllUsers();

        CommonResponse<List<UserResponse>> response = new CommonResponse<>(
                HttpStatus.CREATED.value(),
                "User found",
                allUsers
        );

        return ResponseEntity.ok(response);
    }
}
