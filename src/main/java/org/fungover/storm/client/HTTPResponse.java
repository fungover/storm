package org.fungover.storm.client;

public class HTTPResponse {
    static final String error500 = "HTTP/1.1 500 Internal Server Error, try contacting us at https://fungover.org/ \r\n\r\n";

    String getError500() {
        return error500;
    }
}
