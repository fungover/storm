package org.fungover.storm.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger("SERVER");
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private HttpResponseStatusCodes statusCode;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String input = in.readLine();
            out.println(input);

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            statusCode = new HttpResponseStatusCodes();
            if (e.getMessage().contains("500"))
                LOGGER.error(statusCode.getError500());
            else
                LOGGER.error(e.getMessage());
        }
    }
}
