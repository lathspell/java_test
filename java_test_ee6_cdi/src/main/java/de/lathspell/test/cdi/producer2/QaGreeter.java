package de.lathspell.test.cdi.producer2;

/** The "production" variant of the service. */
@Stage("qa")
class QaGreeter implements Greeting {

    @Override
    public String sayHello() {
        return "Hello tester";
    }
}