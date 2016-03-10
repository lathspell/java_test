package de.lathspell.test.springboot.service;

import org.springframework.data.repository.CrudRepository;

import de.lathspell.test.springboot.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
