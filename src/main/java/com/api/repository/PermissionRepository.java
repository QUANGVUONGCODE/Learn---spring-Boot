package com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {

}
