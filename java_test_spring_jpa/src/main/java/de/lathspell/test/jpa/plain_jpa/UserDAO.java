package de.lathspell.test.jpa.plain_jpa;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import de.lathspell.test.model.User;

@Repository
@Slf4j
public class UserDAO {

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
    public void save(User user) {
        EntityTransaction et = em.getTransaction();
        em.persist(user); // add object to Persistence Context (state "new" to "managed")
        em.flush(); // force write to database to activate auto generated sequences and triggers
        em.refresh(user); // re-read object to update auto generated values like "id"
        et.commit();
    }

    /**
     * UPDATE.
     */
    public void merge(User user) {
        em.merge(user); // merge given object with the managed version from the Persistence Context
        em.flush(); // force write to database to activate auto generated sequences and triggers
        em.refresh(user); // re-read object to update auto generated values like "id"
    }

    /**
     * DELETE.
     */
    public void remove(User user) {
        em.remove(user);
    }

    public User findOne(long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("FROM User u").getResultList();
    }

    public List<User> findAllByUsername(String username) {
        return em.createQuery("FROM User u WHERE u.username = :username").setParameter("username", username).getResultList();
    }

}
