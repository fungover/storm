package org.fungover.storm.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThatNoException;

class ServerTest {
    private static final Logger LOGGER = LogManager.getLogger("SERVER");
    private static int port;

    @BeforeAll
    public static void start() {
        ServerSocket s;

        try {
            s = new ServerSocket(0);
            port = s.getLocalPort();
            s.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        Executors.newSingleThreadExecutor().submit(() -> new Server(port).start());

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    @DisplayName("Connect one client, should not throw exception")
    void connectOneClientShouldNotThrowException() {
        Client client = new Client();

        assertThatNoException().isThrownBy(() -> client.startConnection("localhost", port));
        client.stopConnection();
    }
}
