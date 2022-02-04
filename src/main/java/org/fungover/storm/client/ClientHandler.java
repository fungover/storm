package org.fungover.storm.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fungover.storm.fileHandler.re.FileRequestHandler;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger("SERVER");
    private final Socket clientSocket;
    private OutputStream out;
    private BufferedReader in;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            out = clientSocket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String input = in.readLine();

            FileRequestHandler.handleRequest(input);
            byte[] header = FileRequestHandler.writeResponse()[0];
            byte[] file = FileRequestHandler.writeResponse()[1];

            out.write(header);
            out.write(file);

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
