package de.lathspell.test.jpa.repo2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.lathspell.test.model.User;

public interface UserRepo2 extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    List<User> findAllByUserName(String username);
    
    @Query("SELECT u FROM User u WHERE u.username = :un")
    User findOneByUserName(@Param("un") String username);
}   
