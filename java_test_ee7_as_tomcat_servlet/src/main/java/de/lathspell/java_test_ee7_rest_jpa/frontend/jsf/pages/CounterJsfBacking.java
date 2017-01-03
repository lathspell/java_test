package de.lathspell.java_test_ee7_rest_jpa.frontend.jsf.pages;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class CounterJsfBacking implements Serializable {

    private int counter;
    
    public int inc() {
        return counter++;
    }
}
