package de.lathspell.test.factory;

import java.util.ArrayList;
import java.util.List;

public abstract class Pizza {

    String ort;
    String name;
    String teig;
    String soße;
    List<String> beläge = new ArrayList();

    public void vorbereiten() {
        System.out.println("Bereite " + name);
        System.out.println("Werfe Teig...");
        System.out.println("Füge Soße hinzu...");
        System.out.println("Füge Beläge hinzu: ");
        for (String belag : beläge) {
            System.out.println("   " + belag);
        }
    }

    void backen() {
        System.out.println("Backe 25min bei 350°C");
    }

    void schneiden() {
        System.out.println("Schneide die Pizza diagonal in Stücke");
    }

    void einpacken() {
        System.out.println("Packe die Pizza ein");
    }

    public String getName() {
        return name;
    }
}
