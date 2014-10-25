package de.lathspell.java_test_ee7_rest_jpa.config;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppConfig {

    private final static Logger log = LoggerFactory.getLogger(AppConfig.class);

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
