package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({Lazy2Prototype.class})
@Slf4j
public class Lazy2Configuration2 {

    public Lazy2Configuration2() {
        log.info("ctor");
    }
}
