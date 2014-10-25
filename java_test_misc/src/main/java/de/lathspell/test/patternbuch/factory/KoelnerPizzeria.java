package de.lathspell.test.patternbuch.factory;


class KoelnerPizzeria extends Pizzeria {

    public KoelnerPizzeria() {
    }

    @Override
    public Pizza erstellePizza(String typ) {
        Pizza pizza = new KoelnerPizzaFabrik().erstellePizza(typ);
        return pizza;
    }

    

}
