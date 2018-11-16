package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

import de.lathspell.test.derby.DerbyPersonRepository;
import de.lathspell.test.h2.H2PersonRepository;
import de.lathspell.test.hsql.HsqlPersonRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MultiTest {

    @Autowired
    private DerbyPersonRepository derbyRepo;

    @Autowired
    private H2PersonRepository h2Repo;

    @Autowired
    private HsqlPersonRepository hsqlRepo;

    @Before
    public void before() {
        log.info("+++++++++++++++++++++++++++++++++++");
        derbyRepo.deleteAll();
        h2Repo.deleteAll();
        hsqlRepo.deleteAll();
        log.info("-----------------------------------");
    }

    @Test
    public void testDerbyPerson() {
        log.info("create person");
        de.lathspell.test.derby.DerbyPerson p = new de.lathspell.test.derby.DerbyPerson(null, "Tim", "Taler");
        derbyRepo.save(p);

        assertEquals(1, derbyRepo.count());
        assertEquals(0, h2Repo.count());
        assertEquals(0, hsqlRepo.count());
    }

}
