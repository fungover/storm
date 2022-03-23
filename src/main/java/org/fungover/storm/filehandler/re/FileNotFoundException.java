package org.fungover.storm.filehandler.re;

import java.util.Map;

import static org.fungover.storm.filehandler.re.ResponseCode.NOT_FOUND;

public class FileNotFoundException extends RuntimeException {

    private static final String NOT_FOUND_CODE = NOT_FOUND.getCode();
    private static final String MESSAGE = "File not found";
    private static final String ERROR_404_FILE_NAME = "404.html";
    private final Map<String,String> parsedRequest;
    private final String requestedPath;

    FileNotFoundException(Map<String,String> parsedRequest){
        this.parsedRequest = parsedRequest;
        this.requestedPath = parsedRequest.get("path");
    }

    @Override
    public String getMessage(){
        return MESSAGE;
    }

    public String getError404FileName(){
        return ERROR_404_FILE_NAME;
    }

    public String getResponseCode(){
        return NOT_FOUND_CODE;
    }

    public String getRequestPath() {
        return requestedPath;
    }

    public Map<String,String> getParsedRequest(){
        return parsedRequest;
    }

}
