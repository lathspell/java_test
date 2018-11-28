package de.lathspell.test;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.lathspell.test.db.ArticleRepo;
import de.lathspell.test.model.Article;

@Component
public class ArticleInit {

    @Autowired
    private ArticleRepo repo;
    
    @PostConstruct
    public void init() {
        repo.save(new Article(null, "First Entry", "blah blah", LocalDateTime.now()));
    }
}
