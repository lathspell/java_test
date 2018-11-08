package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Scope(SCOPE_SINGLETON)
@Component()
@Slf4j
public class Lazy2Singleton {

    // @Lazy
    @Autowired
    private Lazy2Prototype myPrototype;

    public Lazy2Singleton() {
        log.info("ctor");
    }
}
