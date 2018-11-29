package de.lathspell.test.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.lathspell.test.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findOneByFirstName(String firstName);
}
