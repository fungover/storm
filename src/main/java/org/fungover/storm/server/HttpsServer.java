package org.fungover.storm.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.concurrent.Executors;

public class HttpsServer extends Server {
    private static final Logger LOGGER = LogManager.getLogger("SERVER");

    public HttpsServer(int port) {
        super(port);
    }

    @Override
    public void start() {
        super.setExecutorService(Executors.newCachedThreadPool());

        try (ServerSocket serverSocket = getServerSocket(super.getPort())) {
            acceptConnections(serverSocket);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    private ServerSocket getServerSocket(int port) throws Exception {
        Path keyStorePath = Path.of("./etc/storm/ssl/keystore.jks");
        char[] keyStorePassword = "password".toCharArray();

        var serverSocket = getSslContext(keyStorePath, keyStorePassword)
                .getServerSocketFactory()
                .createServerSocket(port, 0, new InetSocketAddress(port).getAddress());

        Arrays.fill(keyStorePassword, '0');

        return serverSocket;
    }

    private SSLContext getSslContext(Path keyStorePath, char[] keyStorePassword) throws Exception {
        var keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(keyStorePath.toFile()), keyStorePassword);

        var keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, keyStorePassword);

        var sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        return sslContext;
    }
}
