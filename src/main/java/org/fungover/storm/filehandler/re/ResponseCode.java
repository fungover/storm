package org.fungover.storm.filehandler.re;

public enum ResponseCode {
    OK("200 OK"),
    NOT_FOUND("404 Not Found"),
    UNAUTHORIZED("401 Unauthorized"),
    FORBIDDEN("403 Forbidden"),
    TEAPOT("418 I´m a teapot"),
    INTERNAL_SERVER_ERROR("500 Internal Server Error"),
    HTTP_RESPONSE_STATUS_CODES("500 Internal Server Error\r\n"),
    NOT_IMPLEMENTED("501 Not Implemented");

    private final String code;

    ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
