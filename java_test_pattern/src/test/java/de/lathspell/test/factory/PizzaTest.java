package de.lathspell.test.factory;

import org.junit.Test;
import static org.junit.Assert.*;

public class PizzaTest {

    @Test
    public void testBestellung() {
        Pizzeria pizzeria = new BerlinerPizzeria();
        Pizza p = pizzeria.bestellePizza("Salami");
        assertTrue(p instanceof Pizza);
        assertTrue(p instanceof BerlinerSalamiPizza);
        assertEquals("Berlin", p.ort);

        pizzeria = new KoelnerPizzeria();
        p = pizzeria.bestellePizza("Salami");
        assertTrue(p instanceof Pizza);
        assertTrue(p instanceof KoelnerSalamiPizza);
        assertEquals("Koeln", p.ort);
    }
}
