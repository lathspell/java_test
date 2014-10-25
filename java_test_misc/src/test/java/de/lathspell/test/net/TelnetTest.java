package de.lathspell.test.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Ignore;
import org.junit.Test;

class Servers implements Runnable {
    @Override
    public void run() {
        System.out.println("Servers running!");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(44441);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Servers accepted " + clientSocket.getLocalPort());
                ServerThread serverThread = new ServerThread(clientSocket);
                new Thread(serverThread).run();
            }
        } catch (IOException e) {
            System.out.println("Servers abgebrochen: " + e);
            e.printStackTrace();
        } finally {
            if (serverSocket != null)
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println(e);
                    e.printStackTrace();
                }
        }
    }
}

class ServerThread implements Runnable {
    Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("ServerThread running!");
        OutputStream os = null;
        InputStream is = null;
        PrintWriter pw = null;
        try {
            os = socket.getOutputStream();
            is = socket.getInputStream(); is.close();
            pw = new PrintWriter(os);

            System.out.println("Server writes ...");
            for (int i = 0; i < 5; i++) {
                System.out.println("Server writes line " + i);
                os.write(42); os.flush();
                pw.println("test" + i);
                pw.flush();
                Thread.sleep(500);
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        } finally {
            try {
                if (pw != null) pw.close();
                if (is != null) is.close();
                if (os != null) os.close(); // schließt auch socket
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

}

class Client implements Runnable {

    @Override
    public void run() {
        System.out.println("Client running!");
        Socket client = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        try {
            client = new Socket("localhost", 44441);
            is = client.getInputStream();
            os = client.getOutputStream(); os.close();
            br = new BufferedReader(new InputStreamReader(is));

            System.out.println("Client reads ...");
            for (int i = 0; i < 3; i++) {
                String s = br.readLine();
                System.out.println("Client read line " + i + ": " + s);
            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
                if (is != null) is.close();
                if (client != null) client.close();
            } catch (IOException e) {
                System.err.println(e);
                e.printStackTrace();
            }
        }
    }

}

public class TelnetTest {
    @Ignore
    public void testStart() throws Exception {
        System.out.println("Starting server ...");
        Thread servers = new Thread(new Servers());
        servers.start();

        System.out.println("Starting client ...");
        System.out.flush();
        Thread client = new Thread(new Client());
        client.start();

        System.out.println("Waiting ...");
        client.join();
        servers.join();
    }
}
