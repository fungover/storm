package org.fungover.storm.client;

import nl.altindag.log.LogCaptor;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fungover.storm.fileHandler.re.ResponseCode.ERROR_500;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ClientHandlerTest {


    @Test
    void exceptionWithError500MessageShouldBeThrown() throws IOException {

        Socket clientSocket = Mockito.mock(Socket.class);
        Mockito.when(clientSocket.getOutputStream()).thenThrow(new RuntimeException(ERROR_500.getCode()));
        Throwable exceptionThatWasThrown = assertThrows(RuntimeException.class, clientSocket::getOutputStream);

        assertThat(exceptionThatWasThrown.getMessage()).isEqualTo(ERROR_500.getCode());

    }
}