package de.lathspell.test.jsf.beans;

import lombok.extern.slf4j.Slf4j;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_PROTOTYPE)
@Slf4j
public class AdderPrototypeScopedBean {

    private int number = 0;

    public int inc() {
        return number++;
    }

}
