package de.lathspell.java_test_ee7_rest_jpa.backend.sql;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import de.lathspell.java_test_ee7_rest_jpa.model.Article;

/**
 * Container managed JTA version of the ArticleDAO.
 */
@ApplicationScoped
@Slf4j
public class ArticleJtaDAO implements Serializable {

    @PersistenceContext(unitName = "booksPU")
    private EntityManager em;

    @PostConstruct
    public void postConstruct() {
        if (em == null) {
            throw new RuntimeException("EntityManager is null - this class needs a container managed JTA resource!");
        }
    }

    @Transactional
    public Article save(Article article) {
        em.persist(article);
        em.flush();
        em.refresh(article);
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
