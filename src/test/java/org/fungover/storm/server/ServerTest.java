package org.fungover.storm.server;

import org.fungover.storm.client.Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    @DisplayName("Connect one client to server messages should be the same")
    void connectOneClientToServerMessagesShouldBeTheSame() {
        Server server = new Server(8080);
        server.start();

        Client client1 = new Client();
        client1.startConnection("localhost", 8080);

        String message1 = client1.sendMessage("Hello");
        String message2 = client1.sendMessage("World");
        String terminate = client1.sendMessage("exit");

        assertThat(message1).isEqualTo("Hello");
        assertThat(message2).isEqualTo("World");
        assertThat(terminate).isEqualTo("Disconnecting from server...");
        client1.stopConnection();
        server.stop();
    }


}