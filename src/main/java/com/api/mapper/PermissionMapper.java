package com.api.mapper;

import org.mapstruct.Mapper;

import com.api.dbo.request.PermissionRequest;
import com.api.dbo.response.PermissionReponse;
import com.api.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission MapToPermission(PermissionRequest request);

    PermissionReponse toPermissionResponse(Permission permission);
}