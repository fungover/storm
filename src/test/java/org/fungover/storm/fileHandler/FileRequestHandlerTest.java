package org.fungover.storm.fileHandler;

import org.fungover.storm.client.HttpParser;
import org.fungover.storm.fileHandler.re.FileInfo;
import org.fungover.storm.fileHandler.re.FileRequestHandler;
import org.fungover.storm.fileHandler.re.ResponseCode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class FileRequestHandlerTest {

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
    void pointTo404ErrorPageIfIncorrectFilePathOrUrl() throws IOException {
        FileRequestHandler fileRequestHandler = new FileRequestHandler();
        String req = """
                GET /nonexistantFile.html HTTP/1.1
                Host: storm.fungover.org
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
                Accept-Language: en-US,en;q=0.5
                Connection: keep-alive
                """;
        Map<String, String> parsedRequest = HttpParser.getRequestHeaders(req);
        FileInfo info = fileRequestHandler.handleError(parsedRequest,"404.html", ResponseCode.NOT_FOUND.getCode());
        Path path = info.getPath();
        byte[] file = Files.readAllBytes(path);
        String response = "HTTP/1.1 404 Not Found \r\nContent-length:" + file.length +
            "\r\nContent-type:" + FormatConverter.MIME(path) +
            "\r\nConnection: Closed\r\n\r\n";
        byte[][] expected = new byte[][]{response.getBytes(), file};

        byte[][] result = fileRequestHandler.writeResponse(info, "404 Not Found");

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void absent404ErrorPageInWebrootShouldReturnOnlyCode404() {
        FileRequestHandler fileRequestHandler = new FileRequestHandler();
        String response = "HTTP/1.1 404 Not Found \r\nConnection: Closed\r\n\r\n";
        byte[][] expected = new byte[][]{response.getBytes()};

        byte[][] result = fileRequestHandler.writeResponse("404 Not Found");

        assertThat(result).isEqualTo(expected);
    }

}
