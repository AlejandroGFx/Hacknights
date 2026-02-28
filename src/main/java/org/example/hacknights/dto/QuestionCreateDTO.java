package org.example.hacknights.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record QuestionCreateDTO(
    @NotBlank String questionText,
    List<OptionCreateDTO> options
) {}
