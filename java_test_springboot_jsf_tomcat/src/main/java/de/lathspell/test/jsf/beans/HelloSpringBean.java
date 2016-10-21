package de.lathspell.test.jsf.beans;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;
import org.springframework.stereotype.Component;

@Component
@Scope(value = SCOPE_PROTOTYPE)
public class HelloSpringBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String welcomeMessage = "Spring managed bean";

    public String getWelcomeMessage() {
        return welcomeMessage;
    }
}
