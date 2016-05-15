package de.lathspell.test.jsf.beans;

import javax.inject.Named;
import org.springframework.context.annotation.Scope;

import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

@Named
@Scope(value = SCOPE_SESSION)
public class CounterBean {

    private int counter = 0;

    public int getCounter() {
        return counter++;
    }

}
