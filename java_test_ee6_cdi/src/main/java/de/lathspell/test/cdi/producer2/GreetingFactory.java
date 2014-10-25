package de.lathspell.test.cdi.producer2;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

/**
 * The producer for the type "Greeting".
 *
 * In this example, the implementation is chosen based on the "env" property.
 */
public class GreetingFactory {

    public static String env;
    @Inject
    @Any
    Instance<Greeting> greetings;

    @Produces
    public Greeting getGreeting() {
        System.out.println("Entering getGreeting!\n");
        Instance<Greeting> found = greetings.select(new StageQualifier(env));
        if (!found.isUnsatisfied() && !found.isAmbiguous()) {
            return found.get();
        }
        throw new RuntimeException("Error ...");
    }

    public static class StageQualifier extends AnnotationLiteral<Stage> implements Stage {

        private String value;

        public StageQualifier(String value) {
            this.value = value;
        }

        @Override
        public String value() {
            return value;
        }
    }
}