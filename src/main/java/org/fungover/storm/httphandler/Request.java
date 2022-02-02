package org.fungover.storm.httphandler;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private byte[] body;
    private String url;
    private final Map<String, String> parameters;
    private final Map<String, String> headers;

    public Request() {
        this.headers = new HashMap<String, String>();
        this.parameters = new HashMap<String, String>();
        this.reset();
    }


    public void reset() {
        this.clearBody();
        this.clearHeaders();
        this.clearParameters();
        this.clearUrl();
    }

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

    public void putParameters(String key, String value) {
        this.parameters.put(key,value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void putHeaders(String key, String value) {
        this.headers.put(key,value);
    }

    public void clearBody() {
        this.body = new byte[0];
    }

    public void clearUrl() {
        this.url = "";
    }

    public void clearParameters() {
        this.parameters.clear();
    }

    public void clearHeaders() {
        this.headers.clear();
    }

}
