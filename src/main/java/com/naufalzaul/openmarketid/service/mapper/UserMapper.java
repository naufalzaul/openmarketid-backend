package com.naufalzaul.openmarketid.service.mapper;


import com.naufalzaul.openmarketid.entity.User;
import com.naufalzaul.openmarketid.model.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles().toString()
        );
    }
}
