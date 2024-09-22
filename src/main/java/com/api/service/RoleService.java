package com.api.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.api.dbo.request.RoleRequest;
import com.api.dbo.response.RoleReponse;
import com.api.mapper.RoleMapper;
import com.api.repository.PermissionRepository;
import com.api.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.AccessLevel;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleReponse create(RoleRequest request) {
        var role = roleMapper.MapToRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.MapToRoleReponse(role);
    }

    public List<RoleReponse> readAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::MapToRoleReponse)
                .toList();
    }

    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
