package com.evenly.evenide.code_runner.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CodeExecutionRequestDto {
    private String language;
    private String content;
}
