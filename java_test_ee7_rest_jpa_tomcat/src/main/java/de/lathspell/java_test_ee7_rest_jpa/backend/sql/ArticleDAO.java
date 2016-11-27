package de.lathspell.java_test_ee7_rest_jpa.backend.sql;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import lombok.extern.slf4j.Slf4j;

import de.lathspell.java_test_ee7_rest_jpa.model.Article;

@ApplicationScoped
@Slf4j
public class ArticleDAO implements Serializable {

    @PersistenceUnit(unitName = "myPU")
    private EntityManagerFactory emf;

    private EntityManager em;

    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct");
        if (emf == null) {
            log.info("postConstruct create EMF as @PersistenceUnit did not work!");
            emf = Persistence.createEntityManagerFactory("myPU");
        }
        em = emf.createEntityManager();
    }

    public Article save(Article article) {
        em.getTransaction().begin();
        em.persist(article);
        em.flush();
        em.refresh(article);
        em.getTransaction().commit();
        return article;
    }

    public List<Article> getAll() {
        return em.createQuery("SELECT a FROM Article a", Article.class).getResultList();
    }

    public Article getOneById(Long id) {
        try {
            return em.createQuery("SELECT a FROM Article a WHERE a.id = :id", Article.class).setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
