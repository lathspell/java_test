package de.lathspell.test.springboot.datarest;

import de.lathspell.test.springboot.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "justFullname", types = Person.class)
public interface PersonJustFullnameProjection {

    @Value("#{target.firstName} #{target.lastName}")
    String getFullName();

}
