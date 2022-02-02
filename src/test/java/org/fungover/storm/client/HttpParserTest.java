package org.fungover.storm.client;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HttpParserTest {

    @Test
    void shouldReturnParsedRequestHeaders() {
        String requestHeaders = """
                GET /home.html HTTP/1.1
                Host: developer.mozilla.org
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,/;q=0.8
                Accept-Language: en-US,en;q=0.5
                Connection: keep-alive
                """;

        Map<String, String> result = HttpParser.getRequestHeaders(requestHeaders);
        Map<String, String> expected = Map.of(
                "method", "GET",
                "path", "/home.html",
                "HTTP-version", "HTTP/1.1",
                "Host", "developer.mozilla.org",
                "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,/;q=0.8",
                "Accept-Language", "en-US,en;q=0.5",
                "Connection", "keep-alive"
        );

        assertThat(result).isEqualTo(expected);
    }

}
