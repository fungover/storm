package org.fungover.storm.filehandler.re;

import org.fungover.storm.client.HttpParser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

public class TeapotTest {

    @Test
    public void testIsCoffeeRequest() {
        assertTrue(Teapot.isCoffeeRequest("/coffee"));
        assertFalse(Teapot.isCoffeeRequest("/tea"));
    }

    @Test
    public void testIsCoffeeRequestCaseSensitivity() {
        assertFalse(Teapot.isCoffeeRequest("/Coffee"), "Path matching should be case-sensitive.");
    }

    @Test
    public void testWrite418Response() {
        byte[][] response = Teapot.write418Response();
        String responseString = new String(response[0]);

        assertTrue(responseString.startsWith("HTTP/1.1 418 I'm a Teapot"), "Response should start with 418 status code.");
        assertTrue(responseString.contains("418 I'm a teapot."), "Response should contain the teapot message.");
    }

    @Test
    public void testResponseContentType() {
        byte[][] response = Teapot.write418Response();
        String responseHeaders = new String(response[0]);
        assertTrue(responseHeaders.contains("Content-Type: text/plain"), "Response should specify the correct content type.");
    }

    @Test
    public void testHttpRequestParsingForCoffee() {
        String httpRequest = "GET /coffee HTTP/1.1\r\nHost: localhost\r\n\r\n";
        Map<String, String> parsedHeaders = HttpParser.getRequestHeaders(httpRequest);
        assertTrue(Teapot.isCoffeeRequest(parsedHeaders.get("path")), "The path should be correctly identified from an HTTP request.");
    }

    @Test
    public void testMalformedHttpRequest() {
        String malformedRequest = "GET /coffee"; // Missing HTTP version
        Map<String, String> parsedHeaders = HttpParser.getRequestHeaders(malformedRequest);
        assertTrue(parsedHeaders.isEmpty(), "Malformed requests should not produce valid headers.");
    }
}
