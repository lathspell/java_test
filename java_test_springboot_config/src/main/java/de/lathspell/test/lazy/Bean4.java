package de.lathspell.test.lazy;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Lazy
@Component
@Scope(SCOPE_SINGLETON)
@Getter
@Slf4j
public class Bean4 {

    @Autowired
    private Bean4a bean4a;

    @Autowired
    @Lazy
    private Bean4b bean4b;

    public Bean4() {
        log.info("ctor");
    }

}
