package com.CarManagementApplication.CarManagementApplication.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserResponse {
    private String username;
    private String message;
    private Boolean success;
}
