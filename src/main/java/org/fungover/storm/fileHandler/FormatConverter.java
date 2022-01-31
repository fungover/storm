package org.fungover.storm.fileHandler;

public class FormatConverter {

    public static String MIME(String input){
        String[] result = input.split("\\.");
        String subtype = result[1];
        String type;

        if (subtype.equals("jpeg") || subtype.equals("png"))
            type = "image";
        else if (subtype.equals("html") || subtype.equals("csv"))
            type = "text";
        else
            type = null;

        return type + "/" + subtype;
    }
}
