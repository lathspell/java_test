package de.lathspell.java_test_ee7_rest_jpa.backend.sql;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import lombok.extern.slf4j.Slf4j;

import de.lathspell.java_test_ee7_rest_jpa.model.Article;

/** Resource Local version of the ArticleDAO. */
@ApplicationScoped
@Slf4j
public class ArticleLocalDAO implements Serializable {

    // @PersistenceUnit is not available outside of an application server
    private EntityManagerFactory emf;

    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct: pre emf: " + emf);
        emf = Persistence.createEntityManagerFactory("myContainerPU");
        log.info("postConstruct: post emf: " + emf);
    }

    public Article save(Article article) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(article);
            em.flush();
            em.refresh(article);
            tx.commit();
            return article;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Article> getAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT a FROM Article a", Article.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Article getOneById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT a FROM Article a WHERE a.id = :id", Article.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
