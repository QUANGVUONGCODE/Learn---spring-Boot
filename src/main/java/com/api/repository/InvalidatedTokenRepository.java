package com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.entity.InvalidatedToken;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {

}
