package de.lathspell.test.springboot;

import de.lathspell.test.springboot.datarest.PersonRepository;
import de.lathspell.test.springboot.model.Person;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/** TODO: This does not look very elegant. */
@Component
@Slf4j
public class MyDbInit {

    @Autowired
    private PersonRepository repo;
    
    @Autowired
    public MyDbInit(ApplicationArguments args) {
        log.info("ctor");
    }
    
    @PostConstruct
    public void postConstruct() {
        log.info("postconstruct");
        
        Person p = new Person();
        p.setFirstName("Max");
        p.setLastName("Mustermann");
        repo.save(p);
    }

}