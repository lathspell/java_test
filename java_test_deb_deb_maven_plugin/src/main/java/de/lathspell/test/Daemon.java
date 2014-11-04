package de.lathspell.test;

public class Daemon {

    public static void main(String[] args) throws Exception {
        new Daemon().loop();
    }

    public void loop() throws Exception {
        while (true) {
            System.out.println("looping...");
            Thread.sleep(1000 * 2);
        }
    }
}
