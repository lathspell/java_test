package de.lathspell.test.patternbuch.adapter;

public class StockEnte implements Ente {

    @Override
    public void quaken() {
        System.out.println("Quak");
    }

    @Override
    public void fliegen() {
        System.out.println("Ich fliege");
    }
}
