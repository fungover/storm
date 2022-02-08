package org.fungover.storm.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fungover.storm.fileHandler.re.FileNotFoundException;
import org.fungover.storm.fileHandler.re.FileRequestHandler;
import org.fungover.storm.fileHandler.re.FileInfo;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class ClientHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger("CLIENT_HANDLER");
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
            FileInfo fileInfo;
            byte[][] response;

            try {
                fileInfo = fileRequestHandler.handleRequest(input);
                response = fileRequestHandler.writeResponse(fileInfo);
            } catch (FileNotFoundException e){
                LOGGER.error("File not found: {}", e.getRequestPath());
                response = getFileNotFoundResponse(fileRequestHandler, e);
            }

            out.write(response[0]);
            out.write(response[1]);

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private byte[][] getFileNotFoundResponse(FileRequestHandler fileRequestHandler,
                                             FileNotFoundException e) throws IOException {
        FileInfo fileInfo;
        byte[][] response;
        fileInfo = fileRequestHandler.handleError(e.getParsedRequest(), e.getError404FileName(), e.getResponseCode());
        if(Files.exists(fileInfo.getPath())){
            response = fileRequestHandler.writeResponse(fileInfo, e.getResponseCode());}
        else
            response = fileRequestHandler.writeResponse(e.getResponseCode());
        return response;
    }
}
