package org.fungover.storm.client;

import org.fungover.storm.ServerInitializer;
import org.fungover.storm.server.Client;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ClientHandlerTest {

    private static final int port = ServerInitializer.start();

    @Test
    void clientHandlerShouldCorrectlyParseHTTPRequestHeaders() {
        Client client = new Client();
        client.startConnection("localhost", port);
        String response = client.sendMessage("Hello World!");
        String response2 = client.sendMessage(
                """
                        GET /home.html HTTP/1.1
                        Host: developer.mozilla.org
                        Accept: text/html,application/xhtml+xml,application/xml;q=0.9,/;q=0.8
                        Accept-Language: en-US,en;q=0.5
                        Connection: keep-alive
                        """
        );

        assertThat(response).isEqualTo("Hello World!");

        client.stopConnection();
    }

}
