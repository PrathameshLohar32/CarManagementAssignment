package com.CarManagementApplication.CarManagementApplication.services;

import com.CarManagementApplication.CarManagementApplication.dtos.LoginRequest;
import com.CarManagementApplication.CarManagementApplication.dtos.RegisterUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    ResponseEntity<?> register(RegisterUserRequest user);

    ResponseEntity<?> login(LoginRequest loginRequest);
}
