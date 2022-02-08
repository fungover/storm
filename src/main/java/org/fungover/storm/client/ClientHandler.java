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
    private HttpResponseStatusCodes statusCode;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            out = clientSocket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String input = in.readLine();

            FileInfo fileInfo = FileRequestHandler.handleRequest(input);
            byte[][] response = FileRequestHandler.writeResponse(fileInfo);

            out.write(response[0]);
            out.write(response[1]);

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
