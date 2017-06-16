package de.lathspell.test.service;

import java.util.Date;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import static org.springframework.transaction.TransactionDefinition.ISOLATION_READ_COMMITTED;
import static org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRED;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import de.lathspell.test.jpa.spring_jpa.UserJpaRepo;
import de.lathspell.test.model.User;

@Service
@Slf4j
public class UserService {

    @Autowired
    private PlatformTransactionManager ptm;

    @Autowired
    @Getter
    private UserJpaRepo repo;

    @Transactional
    public User createUserWithDeclarativeTx(String username) {
        // Caveat: DELETE must be flushed, else it's optimized away if JPA "thinks" that there is no object present even if there are rows in the table
        repo.deleteAll();
        repo.flush();

        User u = new User();
        u.setUsername(username);
        u.setCreatedAt(new Date());
        repo.saveAndFlush(u);

        return u;
    }

    public User createUserWithProgrammaticTx(String username) {
        TransactionTemplate tt = new TransactionTemplate(ptm);
        tt.setName("test1");
        tt.setIsolationLevel(ISOLATION_READ_COMMITTED);
        tt.setPropagationBehavior(PROPAGATION_REQUIRED);
        tt.setReadOnly(false);
        tt.setTimeout(30);

        User result = tt.execute((TransactionStatus status) -> {
            repo.deleteAllInBatch();
            repo.flush();

            User user = new User();
            user.setUsername(username);
            user.setCreatedAt(new Date());
            repo.saveAndFlush(user);

            return user;
        });

        return result;
    }

}
