package de.lathspell.test.springboot.datarest;

import de.lathspell.test.springboot.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "virtual", types = {Person.class})
public interface FullnameProjection {

    @Value("#{target.firstName} #{target.lastName}")
    String getFullName();

}
