package com.evenly.evenide.code_runner.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class CodeExecutionRequestDto {
    private String language;
    private String content;
}
