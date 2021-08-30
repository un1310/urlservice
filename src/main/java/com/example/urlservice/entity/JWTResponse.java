package com.example.urlservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class JWTResponse {
    @Id
    private String jwtToken;

    public JWTResponse() {
    }

    public JWTResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}