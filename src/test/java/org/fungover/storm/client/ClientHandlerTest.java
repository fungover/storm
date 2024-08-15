package org.fungover.storm.client;

import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;


class ClientHandlerTest {

    @Test
    void shouldNotLogAnErrorWhenThereIsASuccessfulConnection() throws IOException {
        LogCaptor logCaptor = LogCaptor.forName("SERVER");
        Socket socket = mock(Socket.class);
        Mockito.when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        Mockito.when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("Hello Storm!".getBytes()));
        ClientHandler clientHandler = new ClientHandler(socket);

        clientHandler.run();

        assertThat(logCaptor.getErrorLogs()).isEmpty();
    }

    @Test
    void shouldLogAnErrorWhenThereIsAConnectionError() throws IOException {
        LogCaptor logCaptor = LogCaptor.forName("SERVER");
        Socket socket = new Socket();
        ClientHandler clientHandler = new ClientHandler(socket);

        socket.close();
        clientHandler.run();

        assertThat(logCaptor.getErrorLogs()).hasSize(1);
    }

}
