package de.lathspell.java_test_ee6_jsf_selenium;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class MyBean implements Serializable {

    private String name;

    public MyBean() {
        name = "NoName";
    }
    
    public String update() {
        return "index";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
