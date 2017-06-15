package de.lathspell.test.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.lathspell.test.jpa.spring_jpa.UserJpaRepo;
import de.lathspell.test.model.User;

@Service
public class UserService {

    @Autowired
    private UserJpaRepo repo;
    
    @Transactional
    public User createAndFindUser(String username) {
        User u = new User();
        u.setUsername(username);
        u.setCreatedAt(new Date());
        repo.save(u);

        List<User> list = repo.findAllByUsername(username);
        User u2 = list.get(0);
        return u2;
    }
}
