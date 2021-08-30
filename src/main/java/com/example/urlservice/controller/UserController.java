package com.example.urlservice.controller;

import com.example.urlservice.entity.User;
import com.example.urlservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/url/user")
@Slf4j
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<?> putForCustomer(
            @RequestBody @NonNull User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(
            @RequestBody @NonNull User user) {
        return ResponseEntity.ok(userService.login(user));
    }

    @PostMapping(value = "/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @GetMapping(value= "url-response")
    public ResponseEntity<?> getUser() {
        return ResponseEntity.ok(userService.getUser());
    }

}
