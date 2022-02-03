package org.fungover.storm.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fungover.storm.client.ClientHandler;
import org.fungover.storm.config.Configuration;
import org.fungover.storm.config.ConfigurationManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger LOGGER = LogManager.getLogger("SERVER");
    private final int port;
    private ExecutorService executorService;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            acceptConnections(serverSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptConnections(ServerSocket serverSocket) {
        while (true) {
            try {
                executorService.submit(new ClientHandler(serverSocket.accept()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ConfigurationManager.getInstance().readConfigurationFile("src/main/resources/config.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Starting server...");
        Server server = new Server(conf.getPort());
        server.start();
    }
}
