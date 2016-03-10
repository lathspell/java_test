package de.lathspell.test.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

import de.lathspell.test.springboot.model.Person;
import de.lathspell.test.springboot.service.PersonRepository;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        // Create
        Person person = new Person("Max", "Mustermann", 23);
        person = personRepository.save(person);
        log.info("Person saved with Id: " + person.getId());

        // Retrieve
        for (Person p : personRepository.findAll()) {
            log.info("Found Person: " + p);
        }
    }

}
