package de.lathspell.test.factory;

import de.lathspell.test.factory.Pizza;

class KoelnerSalamiPizza extends Pizza {

    public KoelnerSalamiPizza() {
        name = "Salamipizza Kölner Art";
        teig = "Teig mit dünner Kruste";
        soße = "Tomaten-Soße";

        beläge.add("Geriebener Parmesan");
        beläge.add("Salami in Stücken");
    }

    void schneiden() {
        System.out.println("Schneide die Pizza in rechteckige Stücke");
    }

}
