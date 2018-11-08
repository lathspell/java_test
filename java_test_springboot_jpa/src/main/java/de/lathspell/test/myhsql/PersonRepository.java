package de.lathspell.test.dao.personteam;

import de.lathspell.test.model.personteam.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
}
