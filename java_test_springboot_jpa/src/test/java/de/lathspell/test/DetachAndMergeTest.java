package de.lathspell.test;

import de.lathspell.test.db.AnnouncementRepository;
import de.lathspell.test.model.Announcement;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Detaching and merging Objects.
 *
 * An object is loaded from the database, then "detached" and eventually
 * modified. Later it is "merged" back into the Persistence Context and saved
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@Slf4j
public class DetachAndMergeTest {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private AnnouncementRepository annRepo;

    @Test
    public void test1() {
        // Prepare data
        Announcement origAnn = new Announcement(null, "First", "10100101");
        annRepo.save(origAnn);

        // Load data
        EntityManager em = emf.createEntityManager();
        log.info("EntityManager1: " + em);
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Announcement ann;
        try {
            // Load object using EntityManager
            ann = em.createQuery("SELECT a FROM Announcement a WHERE a.title = ?1", Announcement.class).setParameter(1, "First").getSingleResult();
            assertNotNull(ann);
            assertTrue(em.contains(ann));

            // Detach object to save memory space
            log.warn("Detaching");
            em.detach(ann);
            assertFalse(em.contains(ann));

            tx.commit(); // noop
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        }
        em.close();

        // Modify data
        ann.setWav("111000");

        // Later store it again
        em = emf.createEntityManager();
        log.info("EntityManager2: " + em);
        tx = em.getTransaction();
        tx.begin();
        try {
            assertFalse(em.contains(ann));

            log.warn("Merging");
            ann = em.merge(ann);
            assertTrue(em.contains(ann));

            em.persist(ann);
            em.flush();
            em.refresh(ann);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        }
        em.close();

        // Check if its really in the Database
        assertEquals(1, annRepo.count());
        assertEquals("111000", annRepo.findAll().get(0).getWav());
    }
}
