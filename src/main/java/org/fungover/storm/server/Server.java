package org.fungover.storm.server;

import org.fungover.storm.client.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private ServerSocket serverSocket;
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                new ClientThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8080);
        server.start();
    }
}
