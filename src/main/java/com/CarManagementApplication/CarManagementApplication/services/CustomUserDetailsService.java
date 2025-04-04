package com.CarManagementApplication.CarManagementApplication.services;

import com.CarManagementApplication.CarManagementApplication.entities.User;
import com.CarManagementApplication.CarManagementApplication.exceptions.ResourceNotFoundException;
import com.CarManagementApplication.CarManagementApplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {
        Optional<User> userOptional;

        if (emailOrUsername.contains("@")) {
            userOptional = userRepository.findByEmail(emailOrUsername);
        } else {
            userOptional = userRepository.findById(emailOrUsername);
        }

        User user = userOptional.orElseThrow(() -> new ResourceNotFoundException("User", "email or username", emailOrUsername));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
