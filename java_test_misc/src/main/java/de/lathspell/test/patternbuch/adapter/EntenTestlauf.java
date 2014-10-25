package de.lathspell.test.patternbuch.adapter;

public class EntenTestlauf {

    public static void main(String[] args) {
        StockEnte ente = new StockEnte();

        WilderTruthahn truthahn = new WilderTruthahn();
        Ente truthahnAdapter = new TruthahnAdapter(truthahn);

        System.out.println("Der Truthahn sagt ...");
        truthahn.kollern();
        truthahn.fliegen();

        System.out.println("\nDie Ente sagt ...");
        testeEnte(ente);

        System.out.println("\nDer TruthahnAdapter sagt ...");
        testeEnte(truthahnAdapter);
    }

    private static void testeEnte(Ente ente) {
        ente.quaken();
        ente.fliegen();
    }
}
