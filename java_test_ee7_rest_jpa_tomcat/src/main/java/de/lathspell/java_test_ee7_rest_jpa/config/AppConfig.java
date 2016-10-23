package de.lathspell.java_test_ee7_rest_jpa.config;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppConfig {

    @Produces
    @MyPersistentUnit
    public EntityManager produceEntityManager(InjectionPoint ip) {
        // String name = ip.getAnnotated().getAnnotation(MyPersistentUnit.class).value();
        String name = "myPU";
        log.info("Called with: " + ip + " and name: " + name);

        EntityManager em = Persistence.createEntityManagerFactory(name).createEntityManager();
        log.info("Found em: " + em);
        return em;
    }
}
