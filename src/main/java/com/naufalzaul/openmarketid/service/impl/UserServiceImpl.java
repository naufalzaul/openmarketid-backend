package com.naufalzaul.openmarketid.service.impl;

import com.naufalzaul.openmarketid.entity.User;
import com.naufalzaul.openmarketid.exception.DataNotFoundException;
import com.naufalzaul.openmarketid.model.request.user.UserCreateRequest;
import com.naufalzaul.openmarketid.model.request.user.UserUpdateRequest;
import com.naufalzaul.openmarketid.model.response.UserResponse;
import com.naufalzaul.openmarketid.repository.UserRepository;
import com.naufalzaul.openmarketid.service.UserService;
import com.naufalzaul.openmarketid.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserCreateRequest request) {
        User createNewUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(request.getRole()))
                .build();

        return userRepository.save(createNewUser);
    }

    @Override
    public UserResponse findUserByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException(
                        format("User not found with email %s", email)));

        return userMapper.fromUser(user);
    }

    @Override
    public User updateUser(UserUpdateRequest request) {
        User userById = findUserById(request.getId());
        
        userById.setName(request.getName());
        userById.setEmail(request.getEmail());

        return userRepository.save(userById);
    }

    @Override
    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream().map(userMapper::fromUser).toList();
    }

    @Override
    public User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(
                        format("User not found with id %s", id)));
    }


    @Override
    public void deleteUser(String id) {
        findUserById(id);
        userRepository.deleteById(id);
    }
}
