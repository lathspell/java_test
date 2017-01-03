package de.lathspell.java_test_ee7_rest_jpa.frontend.jsf.pages;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import de.lathspell.java_test_ee7_rest_jpa.backend.sql.ArticleJtaDAO;
import de.lathspell.java_test_ee7_rest_jpa.backend.sql.ArticleLocalDAO;
import de.lathspell.java_test_ee7_rest_jpa.model.Article;

/** Creates new Article. */
@Named
@SessionScoped
@Slf4j
public class NewArticleLocalBacking implements Serializable {

    @Inject
    private ArticleLocalDAO articleLocalDAO;

    @Inject
    private ArticleJtaDAO articleJtaDAO;
    
    @Getter
    private final Article article = new Article();
    
    public void saveLocal() {
        log.info("Save using Resource Local: article=" + article);
        articleLocalDAO.save(article);
        log.info("Save: id=" + article.getId());
    }
    
    public void saveJta() {
        log.info("Save using JTA: article=" + article);
        articleJtaDAO.save(article);
        log.info("Save: id=" + article.getId());
    }
}
