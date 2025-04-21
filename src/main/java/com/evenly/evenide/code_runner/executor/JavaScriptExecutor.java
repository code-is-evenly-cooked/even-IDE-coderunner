package com.evenly.evenide.code_runner.executor;

import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component("javascript")
public class JavaScriptExecutor implements CodeExecutor{

    @Override
    public String execute(String code) throws Exception {
        Path tempDir = Files.createTempDirectory("js-run");
        Path file = tempDir.resolve("script.js");
        Files.writeString(file, code);

        // 실행 단계
        ProcessBuilder builder = new ProcessBuilder(
                "docker", "run", "--rm",
                "--network", "none",
                "--memory", "128m",
                "--cpus", "0.5",
                "--read-only",
                "--pids-limit", "50",
                "--cap-drop", "ALL",
                "--security-opt", "no-new-privileges",
                "-v", tempDir.toAbsolutePath() + ":/app:ro",
                "node:20", "sh", "-c", "timeout 10 node /app/script.js"
        );

        builder.redirectErrorStream(true);
        Process runProcess = builder.start();
        String result = new String(runProcess.getInputStream().readAllBytes());
        runProcess.waitFor();
        return result;
    }
}
