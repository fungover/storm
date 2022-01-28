package org.fungover.storm;

import org.fungover.storm.server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(8080);
        server.start();
    }
}
