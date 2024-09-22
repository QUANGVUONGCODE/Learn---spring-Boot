package com.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.dbo.request.PermissionRequest;
import com.api.dbo.response.PermissionReponse;
import com.api.entity.Permission;
import com.api.mapper.PermissionMapper;
import com.api.repository.PermissionRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.AccessLevel;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionReponse create(PermissionRequest request) {
        Permission permission = permissionMapper.MapToPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionReponse> readAll() {
        return permissionRepository.findAll().stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }

}
