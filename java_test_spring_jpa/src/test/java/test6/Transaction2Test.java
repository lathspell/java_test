package test6;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Transaction Tests.
 *
 * Same as TransactionTest but here the default of rolling back test Transactions
 * is changed at class level. Could as well be put on method level.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Test6Config.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
@Commit // or @Rollback; replacing @TransactionConfiguration(defaultRollback=...)
public class Transaction2Test {

    @Autowired
    private AuthorRepo repo;

    @Before
    public void before() {
        log.info("*** before ***");
        Author a = new Author(null, "Betty");
        repo.save(a);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void test1() {
        log.info("*** test1 ***");
        assertEquals(1, repo.count());
        Author a = new Author(null, "Tim");
        repo.save(a);
        assertEquals(2, repo.count());
    }

    @Test
    @Rollback
    @Transactional
    public void test2() {
        log.info("*** test2 ***");
        assertEquals(3, repo.count());
    }

    @Test
    public void test3() {
        log.info("*** test3 ***");
        assertEquals(3, repo.count());
    }
}
