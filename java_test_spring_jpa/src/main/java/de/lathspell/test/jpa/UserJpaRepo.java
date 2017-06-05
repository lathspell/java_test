package de.lathspell.test.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.lathspell.test.model.User;

/**
 * Spring template for commonly used DAO methods.
 *
 * This interface can be injected using @Autowired and already contains
 * typical methods like save(), update(), delete() and various generic
 * find() variants.
 *
 * The JpaRepository magic does not use Reflection to actually parse
 * the specified entity class ("User") to build methods like findByUsername().
 */
public interface UserJpaRepo extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    public List<User> findAllByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = :un")
    public User findOneByUsername(@Param("un") String username);
}
