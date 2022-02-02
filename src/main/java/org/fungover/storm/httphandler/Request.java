package org.fungover.storm.httphandler;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private byte[] body;
    private String url;
    private Map<String,String> parameters = new HashMap<>();
    private Map<String,String> headers = new HashMap<>();

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
