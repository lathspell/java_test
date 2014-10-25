package de.lathspell.test.patternbuch.command.befehle;


public class KeinBefehl implements Befehl {

    public KeinBefehl() {
    }

    @Override
    public void ausführen() {
        System.out.println("tunix");
    }

    @Override
    public void rückgängig() {
        System.out.println("tunix anders");
    }

}
