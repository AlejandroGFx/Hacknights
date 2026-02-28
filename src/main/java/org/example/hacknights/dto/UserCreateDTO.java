package org.example.hacknights.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(
    @NotBlank String username,
    @NotBlank @Email String email,
    @NotBlank String password
) {}
