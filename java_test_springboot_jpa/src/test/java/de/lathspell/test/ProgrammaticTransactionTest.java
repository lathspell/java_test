package de.lathspell.test;

import de.lathspell.test.db.AnnouncementRepository;
import de.lathspell.test.model.Announcement;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Programmatic transactions with JPA.
 * 
 * Don't do this! Spring has way easier solutions!
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@Slf4j
public class ProgrammaticTransactionTest {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private AnnouncementRepository annRepo;

    @Test
    public void test1() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Announcement ann = new Announcement(null, "test", "101001");
            em.persist(ann);
            em.flush();
            em.refresh(ann);
            tx.commit();
        } catch (Exception e) {
            log.error("While saving ann: " + e.getMessage(), e);
            tx.rollback();
        }

        // test
        assertEquals(1, annRepo.count());
    }
}
