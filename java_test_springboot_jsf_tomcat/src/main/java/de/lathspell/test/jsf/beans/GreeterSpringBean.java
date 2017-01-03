package de.lathspell.test.jsf.beans;

import org.springframework.stereotype.Component;

@Component
public class GreeterSpringBean {

    public String welcomeMessage() {
        return "Hello You!";
    }
}
