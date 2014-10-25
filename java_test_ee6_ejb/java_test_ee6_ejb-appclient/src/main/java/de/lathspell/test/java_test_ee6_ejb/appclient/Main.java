package de.lathspell.test.java_test_ee6_ejb.appclient;

import javax.ejb.EJB;

import de.lathspell.test.java_test_ee6_ejb.ejb.Memo;

public class Main {

    @EJB
    private static Memo memo;

    public static void main(String args[]) {
        System.out.println("Inhalt: " + memo.getAll());
        memo.addItem("test");
        System.out.println("Inhalt: " + memo.getAll());
        memo.removeItem("test");
        System.out.println("Inhalt: " + memo.getAll());
    }
}
