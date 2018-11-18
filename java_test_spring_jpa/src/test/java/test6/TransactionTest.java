package test6;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Transaction Test.
 *
 * By default all methods that run within a transaction are rolled back.
 * Methods not marked with @Transactional are thus not.
 * Methods marked with @Before count into these transactions.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Test6Config.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class TransactionTest {

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

    /** Only the 1 from @Before as test1 was in a Transaction and rolled back. */
    @Test
    public void test2() {
        log.info("*** test2 ***");
        assertEquals(1, repo.count());
    }

    /** Now 3 as test2 was not running inside a transaction and thus there was no rollback! */
    @Test
    public void test3() {
        log.info("*** test3 ***");
        assertEquals(2, repo.count());
    }
}
