package de.lathspell.test.patternbuch.factory;

public abstract class Pizzeria {

    public Pizza bestellePizza(String typ) {
        Pizza pizza = erstellePizza(typ);

        pizza.vorbereiten();
        pizza.backen();
        pizza.schneiden();
        pizza.einpacken();

        return pizza;
    }
    
    abstract Pizza erstellePizza(String typ);
}
