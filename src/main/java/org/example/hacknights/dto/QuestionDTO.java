package org.example.hacknights.dto;

import java.util.List;

public record QuestionDTO(
    Long id,
    String questionText,
    List<OptionDTO> options
) {}
