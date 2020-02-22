package com.chat.server.service.server.socket_factories;

import java.io.*;
import java.net.*;
import java.rmi.server.*;
import javax.net.ssl.*;
import java.security.KeyStore;

public class RMISSLServerSocketFactory implements RMIServerSocketFactory {

    /*
     * Create one SSLServerSocketFactory, so we can reuse sessions
     * created by previous sessions of this SSLContext.
     */
    private SSLServerSocketFactory socketFactory = null;

    public RMISSLServerSocketFactory() throws Exception {
        try {
            // set up key manager to do server authentication
            SSLContext sslContext;
            KeyManagerFactory keyManagerFactory;
            KeyStore keyStore;

            char[] passphrase = "password".toCharArray();
            keyStore = KeyStore.getInstance("JKS");
            keyStore.load(null, passphrase);
            keyStore.store(new FileOutputStream("test_keys"), passphrase);

            keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, passphrase);

            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            socketFactory = sslContext.getServerSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ServerSocket createServerSocket(int port) throws IOException {
        return socketFactory.createServerSocket(port);
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }
}
