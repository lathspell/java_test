package de.lathspell.test;

import de.lathspell.test.myderby.Person;
import de.lathspell.test.myderby.PersonRepository;
import de.lathspell.test.myh2.Team;
import de.lathspell.test.myh2.TeamRepository;
import de.lathspell.test.myhsql.Address;
import de.lathspell.test.myhsql.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MultiTest {

    @Autowired
    private PersonRepository pr;
/*
    @Autowired
    private TeamRepository tr;

    @Autowired
    private AddressRepository ar;
*/
    @Before
    public void before() {
        log.info("+++++++++++++++++++++++++++++++++++");
  //      ar.deleteAll();
    //    tr.deleteAll();
        pr.deleteAll();
        log.info("-----------------------------------");
    }

    @Test
    public void testDerbyPerson() {
        log.info("create person");
        Person p = new Person(null, "Tim", "Taler");
        pr.save(p);

        assertEquals(Long.valueOf(1L), p.getId());
        assertEquals(1, pr.count());
    }
/*
    @Test
    public void testH2Addres() {
        log.info("create address");
        Address a = new Address(null, "London");
        ar.save(a);

        assertEquals(Long.valueOf(1L), a.getId());
        assertEquals(1, ar.count());
    }

    @Test
    public void testHsqlTeam() {
        log.info("create team");
        Team t = new Team(null, "Tomatoes");
        tr.save(t);

        assertEquals(Long.valueOf(1L), t.getId());
        assertEquals(1, tr.count());
    }
*/
}
