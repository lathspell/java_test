package de.lathspell.test.io;

import org.junit.Ignore;

@Ignore
public class ConsoleTest {
    public static void main(String[] args) {
        String name = System.console().readLine("Bitte Namen eingeben: ");
        char[] passArray = System.console().readPassword("Hallo %s, bitte Passwort eingeben: ", name);
        System.out.println("Passwort war: "+new String(passArray));
    }
}
