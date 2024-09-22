package com.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.api.dbo.request.RoleRequest;
import com.api.dbo.response.RoleReponse;
import com.api.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role MapToRole(RoleRequest request);

    RoleReponse MapToRoleReponse(Role role);

}
