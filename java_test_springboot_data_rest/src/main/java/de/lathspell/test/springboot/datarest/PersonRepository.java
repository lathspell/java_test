package de.lathspell.test.springboot.datarest;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.lathspell.test.springboot.model.Person;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * This class generates a REST frontend for the Person entity.
 * 
 * The PersonExcerptProjection is used automatically for displaying entries in
 * a collection like '/persons/', not for individual entries like '/persons/0'.
 * There the Excerpt- or any other projection can be requested by adding a
 * parameter like '/persons/1?projection=justFullname'.
 */
@RepositoryRestResource(collectionResourceRel = "person", path = "/persons", excerptProjection = PersonExcerptProjection.class)
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
