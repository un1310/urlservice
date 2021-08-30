package com.example.urlservice.service;

import com.example.urlservice.entity.JWTResponse;
import com.example.urlservice.entity.User;

public interface UserService {
    JWTResponse login(User user);
    User registerUser(User user);
    User getUser();
    User updateUser(User user);
}
