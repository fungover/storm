package org.fungover.storm.client;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.mock;

class ClientHandlerTest {

    @Test
    void shouldNotThrowExceptionWhenThereIsASuccessfulConnection() throws IOException {
        OutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = new ByteArrayInputStream("hello world".getBytes());
        Socket socket = mock(Socket.class);
        Mockito.when(socket.getOutputStream()).thenReturn(outputStream);
        Mockito.when(socket.getInputStream()).thenReturn(inputStream);
        ClientHandler clientHandler = new ClientHandler(socket);

        assertThatNoException().isThrownBy(clientHandler::run);
    }

}
