package com.CarManagementApplication.CarManagementApplication.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;
}
