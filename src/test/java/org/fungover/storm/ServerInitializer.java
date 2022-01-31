package org.fungover.storm;


import org.fungover.storm.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class ServerInitializer {
    static int port;

    public static int start() {
        ServerSocket s;

        try {
            s = new ServerSocket(0);
            port = s.getLocalPort();
            s.close();
            System.out.println(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Executors.newSingleThreadExecutor().submit(() -> new Server(port).start());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return port;
    }
}
