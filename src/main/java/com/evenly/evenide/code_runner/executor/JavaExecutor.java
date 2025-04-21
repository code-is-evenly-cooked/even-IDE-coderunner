package com.evenly.evenide.code_runner.executor;

import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component("java")
public class JavaExecutor implements CodeExecutor {

    @Override
    public String execute(String code) throws Exception {
        Path tempDir = Files.createTempDirectory("java-run");
        Path file = tempDir.resolve("Main.java");
        Files.writeString(file, code);

        // 컴파일 단계
        ProcessBuilder compileBuilder = new ProcessBuilder(
                "docker", "run", "--rm",
                "--network", "none",
                "--memory", "128m",
                "--cpus", "0.5",
                "--read-only",
                "--pids-limit", "50",
                "--cap-drop", "ALL",
                "--security-opt", "no-new-privileges",
                "-v", tempDir.toAbsolutePath() + ":/app",
                "java-runner", "sh", "-c", "javac /app/Main.java"
        );
        compileBuilder.redirectErrorStream(true);
        Process compileProcess = compileBuilder.start();
        compileProcess.waitFor(); // 실패 시 처리 가능

        // 실행 단계
        ProcessBuilder runBuilder = new ProcessBuilder(
                "docker", "run", "--rm",
                "--network", "none",
                "--memory", "128m",
                "--cpus", "0.5",
                "--read-only",
                "--pids-limit", "50",
                "--cap-drop", "ALL",
                "--security-opt", "no-new-privileges",
                "-v", tempDir.toAbsolutePath() + ":/app:ro",
                "java-runner", "sh", "-c", "timeout 10 java -cp /app Main"
        );


        runBuilder.redirectErrorStream(true);
        Process runProcess = runBuilder.start();
        String result = new String(runProcess.getInputStream().readAllBytes());
        runProcess.waitFor();
        return result;
    }
}
