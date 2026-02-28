package org.example.hacknights.dto;

import java.time.LocalDateTime;

public record LikeDTO(
    Long id,
    LocalDateTime voteDate,
    Long userId,
    Long quizId
) {}
