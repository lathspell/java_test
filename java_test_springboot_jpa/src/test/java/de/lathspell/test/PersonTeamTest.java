package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import de.lathspell.test.db.PersonRepository;
import de.lathspell.test.db.TeamRepository;
import de.lathspell.test.model.Person;
import de.lathspell.test.model.Team;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@Slf4j
public class PersonTeamTest {

    @Autowired
    private PersonRepository personRepo;

    @Autowired
    private TeamRepository teamRepo;

    @Before
    public void before() {
        log.info("+++++++++++++++++++++++++++++++++++");
        teamRepo.deleteAll();
        personRepo.deleteAll();
        log.info("-----------------------------------");
    }

    @Test
    public void testPerson() {
        log.info("create person");
        Person p1 = new Person("Max", "Mustermann", 1967);
        assertEquals(0L, p1.getId());

        log.info("save person");
        Person p1b = personRepo.save(p1);
        log.info("get id from saved person");
        long p1bId = p1b.getId();

        log.info("count persons");
        assertEquals(1, personRepo.count());

        log.info("delete person");
        personRepo.deleteById(p1bId);

        log.info("count persons");
        assertEquals(0, personRepo.count());
    }

    @Test
    public void testPersonInTeam() {
        log.info("create person objects");
        Person p1 = new Person("Max", "Mustermann", 1967);
        Person p2 = new Person("Erika", "Musterfrau", 1953);
        personRepo.save(p1);
        personRepo.save(p2);

        log.info("create team object");
        Team t1 = new Team("Team One");

        log.info("assigning p1 and p2 to Team One");
        t1.getPersons().add(p1);
        t1.getPersons().add(p2);

        log.info("Persisting Team One (also updates team_id on p1 and p2)");
        Team t1saved = teamRepo.saveAndFlush(t1);

        log.info("After saving: t1: " + t1);
        log.info("After saving: p1: " + p1);
        log.info("After saving: p2: " + p2);

        log.info("Verify that p1.team is still NULL");
        assertEquals(2, t1saved.getPersons().size());
        assertNull(p1.getTeam());
        assertNull(p2.getTeam());

        log.info("Verifying that reloading loads Person.team_id");
        Person p1Reloaded = personRepo.findOneByFirstName("Max");
        Person p2Reloaded = personRepo.findOneByFirstName("Erika");
        log.info("After reloading: p1: " + p1Reloaded);
        log.info("After reloading: p2: " + p2Reloaded);
        assertNotNull("FIXME", p1Reloaded.getTeam());
        assertNotNull("FIXME", p2Reloaded.getTeam());
    }
}
