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
import com.api.dbo.request.RoleRequest;
import com.api.dbo.response.RoleReponse;
import com.api.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiRequest<RoleReponse> create(@RequestBody RoleRequest request) {
        return ApiRequest.<RoleReponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiRequest<List<RoleReponse>> readAll() {
        return ApiRequest.<List<RoleReponse>>builder()
                .result(roleService.readAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiRequest<Void> delete(@PathVariable String role) {
        roleService.delete(role);
        return ApiRequest.<Void>builder().build();
    }
}
