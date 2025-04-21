package com.evenly.evenide.code_runner.service;

import com.evenly.evenide.code_runner.dto.CodeExecutionRequestDto;
import com.evenly.evenide.code_runner.dto.CodeExecutionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CodeExecutionServiceTest {

    private final CodeExecutionService codeExecutionService;

    @Autowired
    public CodeExecutionServiceTest(CodeExecutionService codeExecutionService) {
        this.codeExecutionService = codeExecutionService;
    }

    /**
     * JAVA
     *
     */


    // 네트워크 제한
    @Test
    void testNetworkAccessBlocked() throws Exception {
        String code = """
        import java.net.*;
        public class Main {
            public static void main(String[] args) throws Exception {
                new Socket("example.com", 80);
            }
        }
        """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("java");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 네트워크 제한:\n" + response.getResult());
        assertFalse(response.isSuccess());
    }

    // 파일 시스템 제한
    @Test
    void testFileWriteBlocked() throws Exception {
        String code = """
        import java.nio.file.*;
        public class Main {
            public static void main(String[] args) throws Exception {
                Files.writeString(Path.of("/app/test.txt"), "hacked");
            }
        }
        """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("java");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 파일 시스템 제한:\n" + response.getResult());
        assertFalse(response.isSuccess());
    }

    // 권한 제한
    @Test
    void testSystemExitBlocked() throws Exception {
        String code = """
        public class Main {
            public static void main(String[] args) throws Exception {
                Runtime.getRuntime().exec("kill 1");
            }
        }
        """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("java");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 권한 제한:\n" + response.getResult());
        assertFalse(response.isSuccess());
    }

    // CPU/메모리 제한
    @Test
    void testMemoryOveruseBlocked() throws Exception {
        String code = """
        public class Main {
            public static void main(String[] args) {
                int[] arr = new int[1000000000];
            }
        }
        """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("java");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 메모리 제한:\n" + response.getResult());
        assertFalse(response.isSuccess());
    }

    // 프로세스 폭탄 제한
    @Test
    void testProcessForkBombBlocked() throws Exception {
        String code = """
        public class Main {
            public static void main(String[] args) throws Exception {
                while (true) {
                    Runtime.getRuntime().exec("sleep 1");
                }
            }
        }
        """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("java");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 프로세스 폭탄:\n" + response.getResult());
        assertFalse(response.isSuccess());
    }

    // 무한 루프 제한
    @Test
    void testInfiniteLoopTimeoutBlocked() throws Exception {
        String code = """
        public class Main {
            public static void main(String[] args) {
                while (true) {}
            }
        }
        """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("java");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 무한 루프:\n" + response.getResult());
        assertFalse(response.isSuccess());
    }

    /**
     * JS
     *
     */

    @Test
    void testJavaScript_NetworkAccessBlocked() throws Exception {
        String code = """
                fetch('http://example.com');
                """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("javascript");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 testJavaScript_NetworkAccessBlocked 결과:\n" + response.getResult());
        System.out.println("🔥 success: " + response.isSuccess());
        assertFalse(response.isSuccess());
    }

    @Test
    void testJavaScript_FileWriteBlocked() throws Exception {
        String code = """
                require('fs').writeFileSync('/app/hack.txt', 'hacked!');
                """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("javascript");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 testJavaScript_FileWriteBlocked 결과:\n" + response.getResult());
        System.out.println("🔥 success: " + response.isSuccess());
        assertFalse(response.isSuccess());
    }

    @Test
    void testJavaScript_KillBlocked() throws Exception {
        String code = """
                require('child_process').exec('kill 1');
                """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("javascript");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 testJavaScript_KillBlocked 결과:\n" + response.getResult());
        System.out.println("🔥 success: " + response.isSuccess());
        assertFalse(response.isSuccess());
    }

    @Test
    void testJavaScript_MemoryOveruseBlocked() throws Exception {
        String code = """
                let a = []; while (true) { a.push(new Array(1e6).fill(1)); }
                """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("javascript");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 testJavaScript_MemoryOveruseBlocked 결과:\n" + response.getResult());
        System.out.println("🔥 success: " + response.isSuccess());
        assertFalse(response.isSuccess());
    }

    @Test
    void testJavaScript_ForkBombBlocked() throws Exception {
        String code = """
                while (true) { require('child_process').exec('sleep 1'); }
                """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("javascript");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 testJavaScript_ForkBombBlocked 결과:\n" + response.getResult());
        System.out.println("🔥 success: " + response.isSuccess());
        assertFalse(response.isSuccess());
    }

    @Test
    void testJavaScript_InfiniteLoopTimeoutBlocked() throws Exception {
        String code = """
                while (true) {}
                """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("javascript");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 testJavaScript_InfiniteLoopTimeoutBlocked 결과:\n" + response.getResult());
        System.out.println("🔥 success: " + response.isSuccess());
        assertFalse(response.isSuccess());
    }


    /**
     * py
     */

    @Test
    void testPython_NetworkAccessBlocked() throws Exception {
        String code = """
                import socket
                socket.socket().connect(('example.com', 80))
                """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("python");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 testPython_NetworkAccessBlocked 결과:\n" + response.getResult());
        System.out.println("🔥 success: " + response.isSuccess());
        assertFalse(response.isSuccess());
    }

    @Test
    void testPython_FileWriteBlocked() throws Exception {
        String code = """
                open('/app/hack.txt', 'w').write('hacked!')
                """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("python");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 testPython_FileWriteBlocked 결과:\n" + response.getResult());
        System.out.println("🔥 success: " + response.isSuccess());
        assertFalse(response.isSuccess());
    }

    @Test
    void testPython_KillBlocked() throws Exception {
        String code = """
                import os
                os.system('kill 1')
                """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("python");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 testPython_KillBlocked 결과:\n" + response.getResult());
        System.out.println("🔥 success: " + response.isSuccess());
        assertFalse(response.isSuccess());
    }

    @Test
    void testPython_MemoryOveruseBlocked() throws Exception {
        String code = """
                a = []
                while True:
                    a.append([1] * 1000000)
                """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("python");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 testPython_MemoryOveruseBlocked 결과:\n" + response.getResult());
        System.out.println("🔥 success: " + response.isSuccess());
        assertFalse(response.isSuccess());
    }

    @Test
    void testPython_ForkBombBlocked() throws Exception {
        String code = """
                import os
                while True:
                    os.system('sleep 1')
                """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("python");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 testPython_ForkBombBlocked 결과:\n" + response.getResult());
        System.out.println("🔥 success: " + response.isSuccess());
        assertFalse(response.isSuccess());
    }

    @Test
    void testPython_InfiniteLoopTimeoutBlocked() throws Exception {
        String code = """
                while True:
                    pass
                """;

        CodeExecutionRequestDto request = new CodeExecutionRequestDto();
        request.setLanguage("python");
        request.setContent(code);
        CodeExecutionResponse response = codeExecutionService.execute(request);

        System.out.println("🔥 testPython_InfiniteLoopTimeoutBlocked 결과:\n" + response.getResult());
        System.out.println("🔥 success: " + response.isSuccess());
        assertFalse(response.isSuccess());
    }
}