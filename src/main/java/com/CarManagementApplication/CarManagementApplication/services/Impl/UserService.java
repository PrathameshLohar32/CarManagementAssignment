package com.CarManagementApplication.CarManagementApplication.services.Impl;

import com.CarManagementApplication.CarManagementApplication.dtos.LoginRequest;
import com.CarManagementApplication.CarManagementApplication.dtos.LoginResponse;
import com.CarManagementApplication.CarManagementApplication.dtos.RegisterUserRequest;
import com.CarManagementApplication.CarManagementApplication.dtos.RegisterUserResponse;
import com.CarManagementApplication.CarManagementApplication.entities.User;
import com.CarManagementApplication.CarManagementApplication.exceptions.ApiException;
import com.CarManagementApplication.CarManagementApplication.exceptions.ResourceNotFoundException;
import com.CarManagementApplication.CarManagementApplication.repositories.UserRepository;
import com.CarManagementApplication.CarManagementApplication.security.JWTUtils;
import com.CarManagementApplication.CarManagementApplication.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public ResponseEntity<?> register(RegisterUserRequest userRequest) {
        String username = userRequest.getUsername();

        try {
            Optional<User> userOptional = userRepository.findById(userRequest.getUsername());
            if (userOptional.isPresent()) {
                throw new ApiException("Username already exists!");
            }
            Optional<User> userEmailOptional = userRepository.findByEmail(userRequest.getEmail());
            if (userEmailOptional.isPresent()) {
                throw new ApiException("Email already registered!");
            }

            User user = new User();
            user.setUsername(username);
            user.setEmail(userRequest.getEmail());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            throw new ApiException("Error occurred while saving user: " + e.getMessage());
        }
        return ResponseEntity.ok(new RegisterUserResponse(username, "User Registered Successfully", true));
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmailOrUsername(), loginRequest.getPassword()));
            String emailOrUsername = loginRequest.getEmailOrUsername();
            Optional<User> user;
            if (emailOrUsername.contains("@")) {
                user = userRepository.findByEmail(emailOrUsername);
            } else {
                user = userRepository.findById(emailOrUsername);
            }
            if (user.isEmpty()) {
                throw new ResourceNotFoundException("User", "email or username", emailOrUsername);
            }

            String token = jwtUtils.generateToken(user.get());
            LoginResponse loginResponse = new LoginResponse(user.get().getUsername(), token);

            return ResponseEntity.ok(loginResponse);

        } catch (AuthenticationException ex) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new ApiException("Cannot login: " + e.getMessage());
        }
    }
}
