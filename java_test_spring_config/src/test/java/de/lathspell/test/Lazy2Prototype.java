package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Lazy
@Scope(SCOPE_PROTOTYPE)
@Component
@Slf4j
public class Lazy2Prototype {
    public Lazy2Prototype() {
        log.info("ctor");
    }
}
