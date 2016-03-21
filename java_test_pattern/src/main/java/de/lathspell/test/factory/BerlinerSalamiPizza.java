package de.lathspell.test.factory;

import de.lathspell.test.factory.Pizza;

class BerlinerSalamiPizza extends Pizza {

    public BerlinerSalamiPizza() {
        name = "Salamipizza Berliner Art";
        teig = "Teig mit knuspriger Kruste";
        soße = "Marinara-Soße";

        beläge.add("Geriebener Parmesan");
        beläge.add("Salami in Scheiben");
    }

}
