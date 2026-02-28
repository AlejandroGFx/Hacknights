package org.example.hacknights.dto;

import java.util.List;

public record OptionDTO(
    Long id,
    String optionText,
    Boolean isCorrect
) {}
