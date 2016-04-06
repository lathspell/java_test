package de.lathspell.test.lazy;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Lazy // has no effect, prototypes are only instanciated when needed anyways
@Component
@Scope(SCOPE_PROTOTYPE)
@Getter
@Slf4j
public class Bean3 {

    @Autowired
    private Bean3a bean3a;

    @Autowired
    @Lazy
    private Bean3b bean3b;

    public Bean3() {
        log.info("ctor");
    }

}
