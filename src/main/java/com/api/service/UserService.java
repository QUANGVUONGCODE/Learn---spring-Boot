package com.api.service;

import java.util.HashSet;
import java.util.List;

import org.mapstruct.control.MappingControl.Use;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.dbo.request.UserCreationRequest;
import com.api.dbo.request.UserUpdataRequest;
import com.api.dbo.response.UserResponse;
import com.api.entity.User;
import com.api.enums.Role;
import com.api.exception.AppException;
import com.api.exception.ErrorCode;
import com.api.mapper.UserMapper;
import com.api.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse insertUser(UserCreationRequest request) {
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new AppException(ErrorCode.USER_EXISTS);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> readUser() {
        return userRepository.findAll().stream().map(
                userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.userName == authentication.name")
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FOUND_ID)));
    }

    public UserResponse getMyinfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUserName(name).orElseThrow(() -> new AppException(ErrorCode.USERNAME_ERROR));
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String id, UserUpdataRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.FOUND_ID));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new AppException(ErrorCode.FOUND_ID);
        }
        userRepository.deleteById(id);
    }

}
