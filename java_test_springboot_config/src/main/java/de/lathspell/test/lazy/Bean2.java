package de.lathspell.test.lazy;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
@Getter
@Slf4j
public class Bean2 {

    @Autowired
    private Bean2a bean2a;

    @Autowired
    @Lazy
    private Bean2b bean2b;

    public Bean2() {
        log.info("ctor");
    }

}
