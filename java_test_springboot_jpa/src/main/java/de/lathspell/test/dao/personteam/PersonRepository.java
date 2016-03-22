package de.lathspell.test.dao.personteam;

import javax.inject.Named;

import org.springframework.data.repository.CrudRepository;

import de.lathspell.test.model.personteam.Person;

@Named
public interface PersonRepository extends CrudRepository<Person, Long> {
}
