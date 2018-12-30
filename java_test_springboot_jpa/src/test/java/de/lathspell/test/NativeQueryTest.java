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

import de.lathspell.test.db.AnnouncementRepository;
import de.lathspell.test.model.Announcement;

/**
 * Programmatic transactions with JPA.
 *
 * Don't do this! Spring has way easier solutions!
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@Slf4j
public class NativeQueryTest {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private AnnouncementRepository annRepo;

    @Test
    public void test1() {
        // Vorbereiten
        Announcement ann = new Announcement(null, "test", "101001");
        annRepo.deleteAll();
        annRepo.save(ann);
        long id = ann.getId();

        // SELECT
        EntityManager em = emf.createEntityManager();
        Announcement ann2 = (Announcement) em.createNativeQuery("SELECT * FROM Announcement a WHERE id = ?1", Announcement.class).setParameter(1, id).getSingleResult();

        // Test
        assertEquals(ann, ann2);

        // Änderungen
        ann2.setWav("000111");

        // UPDATE
        em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            int affected = em.createNativeQuery("UPDATE Announcement a SET a.wav = ?1 WHERE a.id = ?2")
                    .setParameter(1, ann2.getWav())
                    .setParameter(2, ann2.getId())
                    .executeUpdate();
            assertEquals(1, affected);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }

        // Test
        assertEquals(ann2, annRepo.findById(ann2.getId()).get());
    }
}
