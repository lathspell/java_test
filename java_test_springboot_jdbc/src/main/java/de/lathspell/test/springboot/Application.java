package de.lathspell.test.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

import de.lathspell.test.springboot.model.Person;
import de.lathspell.test.springboot.service.PersonService;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

    @Autowired
    private PersonService personService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        // Create
        Person person = new Person();
        person.setFirstName("Max");
        person.setLastName("Mustermann");
        person.setAge(23);
        int affected = personService.addPerson(person);
        log.info("Person saved; {} rows affected", affected);

        // Retrieve
        for (Person p : personService.getAllPerson()) {
            log.info("Found: " + p);
        }

        // Retrieve as untyped data structure
        List<Map<String, Object>> result2 = personService.getAllPerson2();
        log.info("Found untyped: " + result2);
    }

}
