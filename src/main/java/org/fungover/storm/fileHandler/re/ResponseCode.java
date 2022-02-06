package org.fungover.storm.fileHandler.re;

public enum ResponseCode {
    OK("200 OK"),
    NOT_FOUND("404 Not Found");

    private String code;

    ResponseCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

}
