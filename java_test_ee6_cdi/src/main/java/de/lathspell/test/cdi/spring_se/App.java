package de.lathspell.test.cdi.spring_se;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;

@RequestScoped
public class App {

    @Value(value = "soap.url")
    String url;

    public String getHostnameFromUrl() {
        return url.replaceFirst("^.+?://", "").replaceFirst("/.*$", "");
    }

    public void start() {
        System.out.println("Hostname: " + getHostnameFromUrl());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
