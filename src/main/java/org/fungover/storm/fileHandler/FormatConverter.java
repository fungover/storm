package org.fungover.storm.fileHandler;

public class FormatConverter {

    public static String MIME(String input){
        String[] result = input.split("\\.");

        return "image/" + result[1];
    }
}
