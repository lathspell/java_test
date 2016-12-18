package de.lathspell.java_test_ee7_rest_jpa.frontend.jsf.pages;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import de.lathspell.java_test_ee7_rest_jpa.backend.sql.ArticleDAO;
import de.lathspell.java_test_ee7_rest_jpa.model.Article;

@Named
@SessionScoped
@Slf4j
public class NewArticleBacking implements Serializable {

    @Inject
    private ArticleDAO articleDAO;

    @Getter
    private Article article = new Article();
    
    public void save() {
        log.info("Save: article=" + article);
        articleDAO.save(article);
        log.info("Save: id=" + article.getId());
    }

}
