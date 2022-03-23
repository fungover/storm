package org.fungover.storm.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fungover.storm.client.ClientHandler;
import org.fungover.storm.filehandler.re.FileRequestHandler;
import org.fungover.storm.config.Configuration;
import org.fungover.storm.config.ConfigurationManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public static void main(String[] args) {
        LOGGER.info("Starting server...");
        LOGGER.info("Loading config file");
        Map<String, String> env = System.getenv();

        if (Files.exists(Paths.get("/etc/storm/config/config.json")))
            ConfigurationManager.loadConfigurationFile("/etc/storm/config/config.json");
        else
            ConfigurationManager.loadConfigurationFile("config/config.json");

        Configuration conf = ConfigurationManager.getCurrentConfiguration();

        int port = getPort(env, conf);
        Server server;

        server = getServerType(env, conf, port);

        LOGGER.info("Started server on port: {}", server.getPort());
        server.start();
    }

    private static Server getServerType(Map<String, String> env, Configuration conf, int port) {
        Server server;
        if (env.containsKey("SERVER_TYPE")) {
            String serverType = env.get("SERVER_TYPE");
            if (serverType.equalsIgnoreCase("https")) {
                server = new HttpsServer(port);
            } else {
                server = new Server(port);
            }
        } else if (conf.getType().equals("https")) {
            server = new HttpsServer(port);
        } else {
            server = new Server(port);
        }
        return server;
    }

    private static int getPort(Map<String, String> env, Configuration conf) {
        int port = 8443;
        if (env.containsKey("SERVER_PORT")) {
            try {
                String portEnv = env.get("SERVER_PORT");
                port = Integer.parseInt(portEnv);
            } catch (NumberFormatException e) {
                LOGGER.error("Invalid port! {}", e.getMessage());
            }
        } else if (conf.getPort() != 0) {
            port = conf.getPort();
        }
        return port;
    }

    public int getPort() {
        return port;
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
                executorService.submit(new ClientHandler(serverSocket.accept(), new FileRequestHandler()));
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
