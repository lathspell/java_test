package de.lathspell.java_test_multithread_cobertura;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientServerTest {

    private static final Logger log = LoggerFactory.getLogger(ClientServerTest.class);

    @Test
    public void testClientServer() throws Exception {
        assertEquals("hello world", doConnectWith("HELLO WORLD"));
    }

    private String doConnectWith(String input) throws Exception {
        ClientTask clientTask = new ClientTask(input);
        ServerTask serverTask = new ServerTask();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<?> serverFuture = executorService.submit(serverTask);

        while (!Server.isReady) {
            log.info("Waiting for server to get ready...");
            Thread.sleep(100 * 1);
        }

        Future<String> clientFuture = executorService.submit(clientTask);

        while (!clientFuture.isDone()) {
            log.info("Waiting for client to finish...");
            Thread.sleep(100);
        }

        log.info("Canceling server...");
        serverFuture.cancel(true);

        return clientFuture.get();
    }

    private class ServerTask implements Runnable {

        @Override
        public void run() {
            try {
                Server.main(new String[]{});
            } catch (Exception e) {
                log.warn("Server crashed: ", e);
            }
        }
    }

    private class ClientTask implements Callable {

        private String output;

        public ClientTask(String output) {
            this.output = output;
        }

        @Override
        public String call() throws Exception {
            return new Client().getLowerCase(output);
        }
    }
}
