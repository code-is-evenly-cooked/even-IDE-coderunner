package com.evenly.evenide.code_runner.service;

import com.evenly.evenide.code_runner.dto.CodeExecutionRequestDto;
import com.evenly.evenide.code_runner.dto.CodeExecutionResponse;
import com.evenly.evenide.code_runner.executor.CodeExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CodeExecutionService {

    private final Map<String, CodeExecutor> codeExecutorMap;

    public CodeExecutionResponse execute(CodeExecutionRequestDto requestDto) throws Exception {
        String language = requestDto.getLanguage().toLowerCase();

        CodeExecutor executor = codeExecutorMap.get(language);

        if (executor == null) {
            throw new IllegalArgumentException("지원하지 않는 언어입니다: " + language);
        }

        String result = executor.execute(requestDto.getContent());

        boolean success = !(result.toLowerCase().contains("exception")
                || result.toLowerCase().contains("error")
                || result.toLowerCase().contains("caused by")
                || result.toLowerCase().contains("killed")
                || result.trim().isEmpty());

        return new CodeExecutionResponse(success, result);
    }
}
