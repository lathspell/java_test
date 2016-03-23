package de.lathspell.test.model.personteam;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import de.lathspell.test.dao.personteam.PersonRepository;
import de.lathspell.test.dao.personteam.TeamRepository;
import de.lathspell.test.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@Slf4j
public class PersonTeamTest {

    @Inject
    private PersonRepository pr;

    @Inject
    private TeamRepository tr;

    @Before
    public void before() {
        log.info("+++++++++++++++++++++++++++++++++++");
        tr.deleteAll();
        pr.deleteAll();
        log.info("-----------------------------------");
    }
    
    @Test
    public void testPerson() {
        log.info("create person");
        Person p1 = new Person("Max", "Mustermann", 1967);
        assertEquals(0L, p1.getId());
        
        log.info("save person");
        Person p1b = pr.save(p1);
        log.info("get id from saved person");
        long p1bId = p1b.getId();
        
        log.info("delete person");
        pr.delete(p1bId);
        
        log.info("count persons");
        assertEquals(0, pr.count());
    }
    
    @Test
    public void testPersonInTeam() {
        log.info("create person objects");
        Person p1 = new Person("Max", "Mustermann", 1967);
        Person p2 = new Person("Erika", "Musterfrau", 1953);
        
        log.info("create team object");
        Team t1 = new Team("Team One");
        
        log.info("assigning p1 and p2 to Team One");
        t1.getPersons().add(p1);
        t1.getPersons().add(p2);
        
        log.info("Persisting Team One (also persists p1 and p2)");
        Team t1b = tr.save(t1);
        
        log.info("After saving: t1: " + t1);
        log.info("After saving: p1: " + p1);
        log.info("After saving: p2: " + p2);
        
        log.info("Verify Ids of all objects");
        assertNotEquals(0L, t1.getId());
        assertNotEquals(0L, p1.getId());
        assertNotEquals(0L, p2.getId());
        assertNotEquals(p1.getId(), p2.getId());

        log.info("Team One is ok");
        assertEquals(p1.getId(), t1b.getPersons().get(0).getId());
        assertEquals("Erika", t1b.getPersons().get(1).getFirstName());
        
        log.info("FIXME: The persons are not saved!");
        assertNull(t1b.getPersons().get(0).getTeam());
        assertNull(p1.getTeam());
        assertNull(p2.getTeam());
    }
}
