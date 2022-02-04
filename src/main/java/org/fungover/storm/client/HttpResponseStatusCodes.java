package org.fungover.storm.client;

public class HttpResponseStatusCodes {
    static final String error500 = "HTTP/1.1 500 Internal Server Error\r\n\r\n";

    String getError500() {
        return error500;
    }
}
