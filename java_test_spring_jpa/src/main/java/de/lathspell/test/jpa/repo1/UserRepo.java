package de.lathspell.test.jpa.repo1;

import java.util.List;

import de.lathspell.test.model.User;

public interface UserRepo {

    public List<User> findAllByUsername(String username);

    public void save(User user);

    public User find(long id);

    public List<User> findAll();

    public void merge(User user);

    public void remove(User user);

}
