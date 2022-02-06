package org.fungover.storm.fileHandler;

import org.fungover.storm.fileHandler.re.FileInfo;
import org.fungover.storm.fileHandler.re.FileRequestHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class FileRequestHandlerTest {

    @Test
    void emptyPathShouldPointPathTowardsIndex() throws IOException {
        FileRequestHandler fileRequestHandler = new FileRequestHandler();
        String req = """
                GET / HTTP/1.1
                Host: storm.fungover.org
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
                Accept-Language: en-US,en;q=0.5
                Connection: keep-alive
                """;
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
        FileRequestHandler fileRequestHandler = new FileRequestHandler();
        String req = """
                GET /index.html HTTP/1.1
                Host: storm.fungover.org
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
                Accept-Language: en-US,en;q=0.5
                Connection: keep-alive
                """;
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
    void incorrectFilePathOrUrlShouldPointTo404ErrorPage() throws IOException {
        FileRequestHandler fileRequestHandler = new FileRequestHandler();
        String req = """
                GET /nonexistantFile.html HTTP/1.1
                Host: storm.fungover.org
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
                Accept-Language: en-US,en;q=0.5
                Connection: keep-alive
                """;
        FileInfo info = fileRequestHandler.handleRequest(req);
        byte[][] result = fileRequestHandler.writeResponse(info);
        Path path = info.getPath();
        byte[] file = Files.readAllBytes(path);
        String response = "HTTP/1.1 404 Not Found \r\nContent-length:" + file.length +
            "\r\nContent-type:" + FormatConverter.MIME(path) +
            "\r\nConnection: Closed\r\n\r\n";
        byte[][] expected = new byte[][]{response.getBytes(), file};


        assertThat(result).isEqualTo(expected);
    }
}
