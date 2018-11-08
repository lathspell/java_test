package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Slf4j
public class MethodInjectionTest {

    @Configuration
    @Import({Foo.class, Bar.class})
    static class MyConfiguration {

    }

    @Component
    @Slf4j
    static class Foo {

        private Bar bar;

        public Foo() {
            log.info("Foo ctor: " + this);
        }

        @Autowired
        public void setBar(Bar bar) {
            log.info("Setting bar to: " + bar);
            this.bar = bar;
        }
    }

    @Component
    @Slf4j
    static class Bar {
        public Bar() {
            log.info("Bar ctor: " + this);
        }
    }

    @Test
    public void test1() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MyConfiguration.class);
        log.info("context created");
        Foo foo = ctx.getBean(Foo.class);
        log.info("Foo created with Foo.bar=" + foo.bar);
    }
}
