package com.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dbo.request.ApiRequest;
import com.api.dbo.request.UserCreationRequest;
import com.api.dbo.request.UserUpdataRequest;
import com.api.dbo.response.UserResponse;
import com.api.exception.AppException;
import com.api.exception.ErrorCode;
import com.api.service.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    ApiRequest<UserResponse> insertUser(@RequestBody @Valid UserCreationRequest request) {
        ApiRequest<UserResponse> apiRequest = new ApiRequest<>();
        apiRequest.setResult(userService.insertUser(request));
        return apiRequest;
    }

    @GetMapping
    ApiRequest<List<UserResponse>> readUser() {
        ApiRequest<List<UserResponse>> apiRequest = new ApiRequest<>();
        apiRequest.setResult(userService.readUser());
        return apiRequest;
    }

    @PutMapping("/{id}")
    ApiRequest<UserResponse> updateUser(@PathVariable("id") String id, @RequestBody @Valid UserUpdataRequest request) {
        ApiRequest<UserResponse> apiRequest = new ApiRequest<>();
        apiRequest.setResult(userService.updateUser(id, request));
        return apiRequest;
    }

    @GetMapping("/{id}")
    ApiRequest<UserResponse> getUser(@PathVariable("id") String id) {
        ApiRequest<UserResponse> apiRequest = new ApiRequest<>();
        apiRequest.setResult(userService.getUser(id));
        return apiRequest;
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        throw new AppException(ErrorCode.DELETE_ID);
    }
}
