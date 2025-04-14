package com.evenly.evenide.code_runner.controller;

import com.evenly.evenide.code_runner.dto.CodeExecutionRequestDto;
import com.evenly.evenide.code_runner.dto.CodeExecutionResponse;
import com.evenly.evenide.code_runner.service.CodeExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/code/execute")
public class CodeExecutionController {

    private final CodeExecutionService codeExecutionService;

    @PostMapping
    public ResponseEntity<CodeExecutionResponse> executeCode(
            @RequestBody CodeExecutionRequestDto requestDto
    ) throws Exception {
        CodeExecutionResponse response = codeExecutionService.execute(requestDto);
        return ResponseEntity.ok(response);
    }
}
