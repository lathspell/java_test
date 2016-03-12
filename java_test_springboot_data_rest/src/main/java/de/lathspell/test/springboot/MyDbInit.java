package de.lathspell.test.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import de.lathspell.test.springboot.datarest.PersonRepository;
import de.lathspell.test.springboot.model.Person;

/**
 * Initializes database with sample data.
 *
 * The @Autowired constructor gets started when all Spring components are
 * initialized. As PersonRepository is a required argument, it should have
 * been initialized by Spring before.
 */
@Component
@Slf4j
public class MyDbInit {

    @Autowired
    public MyDbInit(PersonRepository pr) {
        log.info("ctor");

        Person p = new Person();
        p.setFirstName("Max");
        p.setLastName("Mustermann");
        pr.save(p);

        Person p2 = new Person();
        p2.setFirstName("Erika");
        p2.setLastName("Musterfrau");
        pr.save(p2);
    }

}