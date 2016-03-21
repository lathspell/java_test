package de.lathspell.test.jsf.beans;

import java.io.Serializable;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@Component
@Named
@Scope(value = "session")
public class JsfSpringBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String welcomeMessage = "Populated by spring created bean";

    public String getWelcomeMessage() {
        return welcomeMessage;
    }
}
