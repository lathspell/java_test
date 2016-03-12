package de.lathspell.test.springboot.datarest;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.lathspell.test.springboot.model.Person;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "person", path = "/persons")
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

    @RestResource(exported = false)
    List<Person> findByFirstName(String name);

    List<Person> findByLastName(@Param("name") String name);

    @Override
    @RestResource(exported = false)
    void delete(Long id);

    @Override
    @RestResource(exported = false)
    void delete(Person entity);
}
