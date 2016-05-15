package de.lathspell.test.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class HelloJsfBean {

    private final String welcomeMessage = "JSF managed bean";

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

}
