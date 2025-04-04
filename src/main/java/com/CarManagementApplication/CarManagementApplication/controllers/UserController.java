package com.CarManagementApplication.CarManagementApplication.controllers;

import com.CarManagementApplication.CarManagementApplication.dtos.LoginRequest;
import com.CarManagementApplication.CarManagementApplication.dtos.RegisterUserRequest;
import com.CarManagementApplication.CarManagementApplication.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    private ResponseEntity<?> register(@RequestBody RegisterUserRequest user) {

        return userService.register(user);
    }

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest);
    }
}
