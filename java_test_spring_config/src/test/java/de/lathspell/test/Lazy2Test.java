package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Lazy2Test {

    @Test
    public void test1() {
        log.info("*** test1 ***");

        ApplicationContext ctx = new AnnotationConfigApplicationContext(Lazy2Configuration.class);
        log.info("=> context was produced");
        ctx.getBean(Lazy2Singleton.class);
        log.info("=> singleton was produced");
    }

    @Test
    public void test2() {
        log.info("*** test2 ***");

        ApplicationContext ctx = new AnnotationConfigApplicationContext(Lazy2Configuration2.class);
        log.info("=> context was produced");
        ctx.getBean(Lazy2Prototype.class);
        log.info("=> prototype was produced");
    }
}
