package org.fungover.storm.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fungover.storm.client.ClientHandler;
import org.fungover.storm.config.Configuration;
import org.fungover.storm.config.ConfigurationManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
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
            LOGGER.error(e.getMessage());
        }
    }

    public void acceptConnections(ServerSocket serverSocket) {
        while (!serverSocket.isClosed()) {
            try {
                executorService.submit(new ClientHandler(serverSocket.accept()));
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        LOGGER.info("Starting server...");
        Map<String, String> env = System.getenv();
        Server server = new Server(getPort(env));
        LOGGER.info("Started server on port: {}", server.port);
        server.start();
    }

    private static int getPort(Map<String, String> env) {
        ConfigurationManager.getInstance().readConfigurationFile("etc/storm/config/config.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        int port = 8080;
        if (env.containsKey("SERVER_PORT")) {
            try {
                String portEnv = env.get("SERVER_PORT");
                port = Integer.parseInt(portEnv);
            } catch (NumberFormatException e) {
                LOGGER.error("Invalid port! {}", e.getMessage());
            }
        } else if (conf.getPort() != 0){
            port = conf.getPort();
        }
        return port;
    }
}
