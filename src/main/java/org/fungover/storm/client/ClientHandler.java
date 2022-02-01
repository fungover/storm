package org.fungover.storm.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final Map<String, String> requestHeaders;
    int count;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        requestHeaders = new HashMap<>();
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String input;
            while ((input = in.readLine()) != null) {
                if(input.isEmpty())
                    break;

                parseHeader(input);

                out.println(input); //

                // parse pair into map
            };
            printRequestHeaders();

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseHeader(String input) {
        requestHeaders.put(String.valueOf(count++), input);

        //check if valid header
            //check for GET PUT etc

        //add to requestHeaders map

    }

    public void printRequestHeaders() {
        if(requestHeaders.isEmpty())
            System.out.println("No headers found");
        else
            requestHeaders.forEach((a, b) -> System.out.println(a + " - " + b));
    }

}
