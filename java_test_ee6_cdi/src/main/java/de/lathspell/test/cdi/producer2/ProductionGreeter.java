package de.lathspell.test.cdi.producer2;

/** The "production" variant of the service. */
@Stage("production")
class ProductionGreeter implements Greeting {

    @Override
    public String sayHello() {
        return "Hello customer";
    }
}