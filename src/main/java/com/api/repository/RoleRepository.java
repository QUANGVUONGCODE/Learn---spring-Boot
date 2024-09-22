package com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
