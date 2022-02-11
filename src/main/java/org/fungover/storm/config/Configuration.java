package org.fungover.storm.config;

public class Configuration {
    private int port;
    private String webroot;
    private String type;

    public int getPort() {
        return port;
    }

    public Configuration setPort(int port) {
        this.port = port;
        return this;
    }

    public String getWebroot() {
        return webroot;
    }

    public Configuration setWebroot(String webroot) {
        this.webroot = webroot;
        return this;
    }

    public String getType() {
        return type;
    }

    public String setType(String type) {
        this.type = type;
        return type;
    }
}
