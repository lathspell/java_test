package de.lathspell.test.patternbuch.factory;

public class KoelnerPizzaFabrik {

    public Pizza erstellePizza(String typ) {
        Pizza pizza = null;

        if (typ.equals("Salami")) {
            pizza = new KoelnerSalamiPizza();
        } else if (typ.equals("Schinken")) {
            pizza = new SchinkenPizza();
        } else if (typ.equals("Thunfisch")) {
            pizza = new ThunfischPizza();
        } else if (typ.equals("Krabben")) {
            pizza = new KrabbenPizza();
        }

        pizza.ort = "Koeln";
        
        return pizza;
    }
}
