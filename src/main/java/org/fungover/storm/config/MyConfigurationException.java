package org.fungover.storm.config;

public class MyConfigurationException extends RuntimeException {
    public MyConfigurationException() {
    }

    public MyConfigurationException(String message) {
        super(message);
    }

    public MyConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyConfigurationException(Throwable cause) {
        super(cause);
    }
}
