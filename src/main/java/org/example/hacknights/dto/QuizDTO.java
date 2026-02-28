package org.example.hacknights.dto;

import java.time.LocalDateTime;
import java.util.List;

public record QuizDTO(
    Long id,
    String theme,
    LocalDateTime createdAt,
    Integer likesCount,
    Long userId,
    List<QuestionDTO> questions
) {}
