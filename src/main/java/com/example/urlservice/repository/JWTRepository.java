package com.example.urlservice.repository;

import com.example.urlservice.entity.JWTResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JWTRepository extends JpaRepository<JWTResponse, String> {
}

