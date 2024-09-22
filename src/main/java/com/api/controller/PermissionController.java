package com.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dbo.request.ApiRequest;
import com.api.dbo.request.PermissionRequest;
import com.api.dbo.response.PermissionReponse;
import com.api.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    PermissionService permissionService;

    @PostMapping
    ApiRequest<PermissionReponse> create(@RequestBody PermissionRequest request) {
        return ApiRequest.<PermissionReponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiRequest<List<PermissionReponse>> readAll() {
        return ApiRequest.<List<PermissionReponse>>builder()
                .result(permissionService.readAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiRequest<Void> delete(@PathVariable String permission) {
        permissionService.delete(permission);
        return ApiRequest.<Void>builder()
                .build();
    }
}
