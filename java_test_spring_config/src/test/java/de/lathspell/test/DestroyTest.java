package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.junit.Assert.assertEquals;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

@Slf4j
public class DestroyTest {

    @Lazy
    @Component
    @Scope(SCOPE_PROTOTYPE)
    static class Foo implements DisposableBean {
        public Foo() {
            log.info("ctor");
        }

        @Override
        public void destroy() {
            log.info("destroy");
            DestroyTest.destroyed++;
        }
    }

    @Lazy
    @Component
    @Scope(SCOPE_SINGLETON)
    static class Bar implements DisposableBean {
        public Bar() {
            log.info("ctor");
        }

        @Override
        public void destroy() {
            log.info("destroy");
            DestroyTest.destroyed++;
        }
    }

    static int destroyed = 0;

    @Test
    public void testPrototype() {
        destroyed = 0;
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Foo.class);
        ctx.refresh();
        ctx.getBean(Foo.class);
        ctx.close();
        assertEquals(0, destroyed);
    }

    @Test
    public void testSingleton() {
        destroyed = 0;
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(Bar.class);
        ctx.refresh();
        ctx.getBean(Bar.class);
        ctx.close();
        assertEquals(1, destroyed);
    }
}
