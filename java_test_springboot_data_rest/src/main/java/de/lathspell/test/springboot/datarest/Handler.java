package de.lathspell.test.springboot.datarest;

import de.lathspell.test.springboot.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

@RepositoryEventHandler
@Slf4j
public class Handler {

    @HandleBeforeSave
    public void handlePersonSave(Person p) {
        log.info("About to save Person: " + p);
    }
}
