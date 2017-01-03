package de.lathspell.java_test_ee7_rest_jpa.frontend.jsf.pages;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class CounterCdiBacking implements Serializable {

    private int counter;
    
    public int inc() {
        return counter++;
    }
}
