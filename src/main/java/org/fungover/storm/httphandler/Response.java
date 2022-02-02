package org.fungover.storm.httphandler;

import java.util.Map;

public class Response {
    private Map<String, String> headers;
    private int statusCode;
    private byte[] body;


    public Response(Map<String, String> headers, int statusCode, byte[] body) {
        this.headers = headers;
        this.statusCode = statusCode;
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

}
