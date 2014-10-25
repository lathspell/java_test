package de.lathspell.test.cdi.producer;

import de.lathspell.test.cdi.helper.PreferredProducedGreeter;
import javax.enterprise.inject.Produces;


public class ProducedGreeterFactory {
    private static int counter = 0;

    @Produces @PreferredProducedGreeter
    public ProducedGreeter createGreeter() {
        if (((counter++) % 2) == 0) {
            return new ProducedGreeterGerman();
        } else {
            return new ProducedGreeterEnglish();
        }
    }
}
