package de.lathspell.test.jpa.repo1;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import de.lathspell.test.model.User;

@Repository
@Slf4j
public class UserRepoJpa implements UserRepo {

    @PersistenceContext// (unitName = "entityManagerFactory") // Not "userPU" as set inside JpaConfiguration.entityManagerFactory() !!!
    private EntityManager em;

    @PostConstruct
    public void postConstruct() {
        if (em == null) {
            throw new RuntimeException("EntityManager is null - this class needs a container managed JTA resource!");
        }
    }

    /**
     * INSERT.
     */
    @Override
    public void save(User user) {
        em.persist(user); // add object to Persistence Context (state "new" to "managed")
        em.flush(); // force write to database to activate auto generated sequences and triggers
        em.refresh(user); // re-read object to update auto generated values like "id"
    }

    /**
     * UPDATE.
     */
    @Override
    public void merge(User user) {
        em.merge(user); // merge given object with the managed version from the Persistence Context
        em.flush(); // force write to database to activate auto generated sequences and triggers
        em.refresh(user); // re-read object to update auto generated values like "id"
    }

    /**
     * DELETE.
     */
    @Override
    public void remove(User user) {
        em.remove(user);
    }

    @Override
    public User find(long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("FROM User u").getResultList();
    }

    @Override
    public List<User> findAllByUsername(String username) {
        return em.createQuery("FROM User u WHERE u.username = :username").setParameter("username", username).getResultList();
    }

}
