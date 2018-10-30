package test2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test2.model.Pet;
import test2.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class UserService {

    @PersistenceContext
    private EntityManager em;


    public User findUserById(long id) {
        return em.find(User.class, id);
    }

    public User storeUser(User user) {
        em.persist(user);
        em.flush();
        em.refresh(user);
        return user;
    }

    public List<User> getAllUsers() {
        return em.createQuery("SELECT u FROM User u ORDER BY u.id", User.class).getResultList();
    }

}
