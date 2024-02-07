package org.fungover.storm.filehandler.re;

public class Teapot {


    public static boolean isCoffeeRequest(String requestPath) {
        return "/coffee".equals(requestPath);
    }


    public static byte[][] write418Response() {
        String response = "HTTP/1.1 418 I'm a Teapot\r\n" +
                "Content-Type: text/plain\r\n" +
                "Connection: close\r\n\r\n" +
                "418 I'm a teapot.";
        return new byte[][]{response.getBytes()};
    }
}