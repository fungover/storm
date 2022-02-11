package org.fungover.storm.fileHandler.re;

public enum ResponseCode {
    OK("200 OK"),
    NOT_FOUND("404 Not Found"),
    UNAUTHORIZED("401 Unauthorized"),
    FORBIDDEN("403 Forbidden"),
    TEAPOT("418 I´m a teapot"),
    INTERNAL_SERVER_ERROR("500 Internal Server Error"),
    NOT_IMPLEMENTED("501 Not Implemented");


    private final String code;

    ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
