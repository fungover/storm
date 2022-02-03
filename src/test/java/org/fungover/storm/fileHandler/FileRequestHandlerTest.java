package org.fungover.storm.fileHandler;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class FileRequestHandlerTest {

    @Test
    void emptyPathShouldPointPathTowardsIndex() throws IOException {
        String req = """
                GET / HTTP/1.1
                Host: storm.fungover.org
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
                Accept-Language: en-US,en;q=0.5
                Connection: keep-alive
                """;
        FileRequestHandler.handleRequest(req);
        byte[][] result = FileRequestHandler.writeResponse();
        Path path = FileRequestHandler.getPath();
        byte[] file = Files.readAllBytes(path);
        String response = "HTTP/1.1 200 OK \r\nContent-length:" + file.length +
                "Content-type:" + FormatConverter.MIME(path) +
                "\r\nConnection: Closed\r\n\r\n";
        byte[][] expected = new byte[][]{response.getBytes(), file};


        assertThat(result).isEqualTo(expected);
    }
}
