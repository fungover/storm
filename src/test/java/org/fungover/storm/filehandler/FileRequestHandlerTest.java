package org.fungover.storm.filehandler;

import org.fungover.storm.filehandler.re.FileInfo;
import org.fungover.storm.filehandler.re.FileRequestHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class FileRequestHandlerTest {

    @Test
    void emptyPathShouldPointPathTowardsIndex() throws IOException {
        String req = """
                GET / HTTP/1.1
                Host: storm.fungover.org
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
                Accept-Language: en-US,en;q=0.5
                Connection: keep-alive
                """;
        FileRequestHandler fileRequestHandler = new FileRequestHandler();
        FileInfo info = fileRequestHandler.handleRequest(req);
        byte[][] result = fileRequestHandler.writeResponse(info);
        Path path = info.getPath();
        byte[] file = Files.readAllBytes(path);
        String response = "HTTP/1.1 200 OK \r\nContent-length:" + file.length +
                "\r\nContent-type:" + FormatConverter.MIME(path) +
                "\r\nConnection: Closed\r\n\r\n";
        byte[][] expected = new byte[][]{response.getBytes(), file};


        assertThat(result).isEqualTo(expected);
    }

    @Test
    void indexPathShouldPointTowardsIndex() throws IOException {
        String req = """
                GET /index.html HTTP/1.1
                Host: storm.fungover.org
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
                Accept-Language: en-US,en;q=0.5
                Connection: keep-alive
                """;
        FileRequestHandler fileRequestHandler = new FileRequestHandler();
        FileInfo info = fileRequestHandler.handleRequest(req);
        byte[][] result = fileRequestHandler.writeResponse(info);
        Path path = info.getPath();
        byte[] file = Files.readAllBytes(path);
        String response = "HTTP/1.1 200 OK \r\nContent-length:" + file.length +
                "\r\nContent-type:" + FormatConverter.MIME(path) +
                "\r\nConnection: Closed\r\n\r\n";
        byte[][] expected = new byte[][]{response.getBytes(), file};


        assertThat(result).isEqualTo(expected);
    }
}
