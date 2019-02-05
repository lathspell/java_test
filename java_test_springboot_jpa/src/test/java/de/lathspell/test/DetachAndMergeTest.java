package de.lathspell.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import de.lathspell.test.db.AnnouncementRepository;
import de.lathspell.test.model.Announcement;

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

    /** Somebody changes the detached object in the database while we change it as well -> we win. */
    @Test
    public void test2() {
        // Prepare data
        annRepo.deleteAll();
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

        // Data gets modified by somebody else
        Announcement other = annRepo.findById(ann.getId()).get();
        other.setWav("00001");
        annRepo.save(other);
        assertEquals("111000", ann.getWav()); // does not know about external modification

        // Later store it again
        em = emf.createEntityManager();
        log.info("EntityManager2: " + em);
        tx = em.getTransaction();
        tx.begin();
        try {
            assertFalse(em.contains(ann));

            log.warn("Merging");
            ann = em.merge(ann);
            assertEquals("111000", ann.getWav()); // We have kept our value despite the external modification
            assertTrue(em.contains(ann));

            em.persist(ann);
            em.flush();
            em.refresh(ann);
            assertEquals("111000", ann.getWav()); // Out modification won over the external one

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

    /** Merging an object that was never detached. */
    @Test
    public void test3() {
        // Prepare data
        annRepo.deleteAll();
        Announcement origAnn = new Announcement(null, "First", "10100101");
        annRepo.save(origAnn);

        // Load object
        EntityManager em = emf.createEntityManager();
        Announcement ann = em.createQuery("SELECT a FROM Announcement a WHERE a.title = :title", Announcement.class)
                .setParameter("title", "First")
                .getSingleResult();
        assertNotNull(ann.getId());
        assertEquals("First", ann.getTitle());
        assertEquals("10100101", ann.getWav());

        // Modify objet
        ann.setWav("000111");

        // Persist it using merge(!)
        ann = em.merge(ann);
        em.flush();
        em.refresh(ann);

        // Check
        assertEquals("000111", annRepo.findById(origAnn.getId()));
    }
}
