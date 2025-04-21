package com.evenly.evenide.code_runner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CodeExecutionResponse {
    private final boolean success;
    private final String result;
}
