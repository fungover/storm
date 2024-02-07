package org.fungover.storm.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fungover.storm.filehandler.re.FileInfo;
import org.fungover.storm.filehandler.re.FileNotFoundException;
import org.fungover.storm.filehandler.re.FileRequestHandler;
import org.fungover.storm.filehandler.re.Teapot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;

public class ClientHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger("CLIENT_HANDLER");
    private final Socket clientSocket;
    private HttpResponseStatusCodes statusCode;
    private FileRequestHandler fileRequestHandler;

    public ClientHandler(Socket socket, FileRequestHandler fileRequestHandler) {
        this.clientSocket = socket;
        this.fileRequestHandler = fileRequestHandler;
    }

    @Override
    public void run() {
        OutputStream out;
        BufferedReader in;

        try {
            out = clientSocket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String input = in.readLine(); // This reads the request line
            byte[][] response;

            // Update the method call to match the newly added method in HttpParser
            if (Teapot.isCoffeeRequest(HttpParser.getPath(input))) { // Now correctly calling getPath
                response = Teapot.write418Response();
            } else {
                response = getResponse(input);
            }

            out.write(response[0]); // Write headers
            if (response.length > 1 && response[1] != null) {
                out.write(response[1]); // Write body if it exists
            }

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

    private byte[][] getResponse(String input) throws IOException {
        FileInfo fileInfo;
        byte[][] response;
        try {
            LOGGER.info("INPUT: " + input);
            fileInfo = fileRequestHandler.handleRequest(input);
            response = fileRequestHandler.writeResponse(fileInfo);
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found: {}", e.getRequestPath());
            response = getFileNotFoundResponse(e);
        }
        return response;
    }

    private byte[][] getFileNotFoundResponse(FileNotFoundException e) throws IOException {
        FileInfo fileInfo = fileRequestHandler.handleError(e.getParsedRequest(), e.getError404FileName(), e.getResponseCode());
        byte[][] response;
        if (Files.exists(fileInfo.getPath())) {
            response = fileRequestHandler.writeResponse(fileInfo, e.getResponseCode());
        } else {
            response = fileRequestHandler.writeResponse(e.getResponseCode());
        }
        return response;
    }
}
