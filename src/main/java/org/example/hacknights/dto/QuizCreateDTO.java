package org.example.hacknights.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record QuizCreateDTO(
    @NotBlank String theme,
    @NotNull Long userId,
    List<QuestionCreateDTO> questions
) {}
