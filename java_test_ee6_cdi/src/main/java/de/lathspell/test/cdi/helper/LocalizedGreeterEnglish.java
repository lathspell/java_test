package de.lathspell.test.cdi.helper;

import javax.enterprise.inject.Alternative;

@Alternative
public class LocalizedGreeterEnglish implements LocalizedGreeter {

    @Override
    public String getGreeting() {
        return "Hallo";
    }
}
