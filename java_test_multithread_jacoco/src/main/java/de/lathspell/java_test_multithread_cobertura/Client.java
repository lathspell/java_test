package de.lathspell.java_test_multithread_cobertura;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) throws Exception {
        log.info("GOT: " + (new Client()).getLowerCase("HELLO WORLD"));
    }

    public String getLowerCase(String output) throws Exception {
        log.info("C: getLowerCase(" + output + ")");
        try (Socket socket = new Socket("localhost", Server.PORT)) {
            log.info("C: Connected to " + socket.getRemoteSocketAddress());

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.write(output + "\n");
            out.flush();
            log.info("C: sent:     " + output);

            String input = in.readLine();
            log.info("C: received: " + input);

            log.info("C: getLowerCase() => " + input);
            return input;
        }

    }
}
