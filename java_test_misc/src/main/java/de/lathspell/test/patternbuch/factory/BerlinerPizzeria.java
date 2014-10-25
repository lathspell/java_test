package de.lathspell.test.patternbuch.factory;


class BerlinerPizzeria extends Pizzeria {

    public BerlinerPizzeria() {
    }

    @Override
    public Pizza erstellePizza(String typ) {
        Pizza pizza = new BerlinerPizzaFabrik().erstellePizza(typ);
        // tune pizza
        return pizza;
    }

    

}
