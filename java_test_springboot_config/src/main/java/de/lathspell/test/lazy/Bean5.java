package de.lathspell.test.lazy;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Component
@Scope(SCOPE_SINGLETON)
@Getter
@Slf4j
public class Bean5 {

    @Autowired
    private Bean5a bean5a;

    @Autowired
    @Lazy
    private Bean5b bean5b;

    public Bean5() {
        log.info("ctor");
    }

}
