package org.fungover.storm.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fungover.storm.fileHandler.re.FileRequestHandler;
import org.fungover.storm.fileHandler.re.FileInfo;

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
            FileRequestHandler fileRequestHandler = new FileRequestHandler();
            out = clientSocket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String input = in.readLine();

            FileInfo fileInfo = fileRequestHandler.handleRequest(input);
            byte[][] response = fileRequestHandler.writeResponse(fileInfo);

            out.write(response[0]);
            out.write(response[1]);

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
