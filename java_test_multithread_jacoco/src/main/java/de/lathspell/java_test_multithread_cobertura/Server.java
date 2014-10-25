package de.lathspell.java_test_multithread_cobertura;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

    public static final int PORT = 1235;
    public static boolean isReady = false;
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws Exception {
        log.info("S: Initializing Server");
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(PORT));
        Thread.sleep(500); // do something
        isReady = true;

        while (true) {
            log.info("S: Waiting for client connection.");
            try (Socket clientSocket = serverSocket.accept()) {
                log.info("S: Got connection from " + clientSocket.getRemoteSocketAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                String input = in.readLine();
                log.info("S: received: " + input);

                Thread.sleep(100); // do something
                String output = input.toLowerCase();
                log.info("S: sending:  " + output);
                out.write(output + "\n");
                out.flush();
            }
        }
    }
}
