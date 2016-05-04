package de.lathspell.test.java_test_ee7_payaramicro.gui;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class HelloBacking implements Serializable {

    private static final long serialVersionUID = 1L;

    public String getGreeting(String input) {
        return input.toUpperCase();
    }

}
