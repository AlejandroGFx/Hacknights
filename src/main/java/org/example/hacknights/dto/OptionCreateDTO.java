package org.example.hacknights.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OptionCreateDTO(
    @NotBlank String optionText,
    @NotNull Boolean isCorrect
) {}
