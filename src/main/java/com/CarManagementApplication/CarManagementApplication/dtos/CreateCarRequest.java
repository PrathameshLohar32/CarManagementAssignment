package com.CarManagementApplication.CarManagementApplication.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;


    @ElementCollection
    private List<String> tags;
}
