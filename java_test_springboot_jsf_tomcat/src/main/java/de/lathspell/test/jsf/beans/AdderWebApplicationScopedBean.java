package de.lathspell.test.jsf.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import static org.springframework.web.context.WebApplicationContext.SCOPE_APPLICATION;

@Component
@Scope(SCOPE_APPLICATION)
@Slf4j
public class AdderWebApplicationScopedBean {

    private int number = 0;

    public int inc() {
        return number++;
    }

}
