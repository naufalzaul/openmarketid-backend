package com.naufalzaul.openmarketid.service;

import com.naufalzaul.openmarketid.entity.User;
import com.naufalzaul.openmarketid.model.request.user.UserCreateRequest;
import com.naufalzaul.openmarketid.model.request.user.UserUpdateRequest;
import com.naufalzaul.openmarketid.model.response.UserResponse;

import java.util.List;

public interface UserService {

    User createUser (UserCreateRequest request);
    User updateUser (UserUpdateRequest request);
    List<UserResponse> findAllUsers ();
    User findUserById (String id);
    UserResponse findUserByEmail ();
    void deleteUser (String id);
}
