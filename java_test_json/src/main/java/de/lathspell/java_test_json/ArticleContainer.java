package de.lathspell.java_test_json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class ArticleContainer {

    private List<Article> articles;

    @JsonCreator
    public ArticleContainer(List<Article> articles) {
        this.articles = articles;
    }

    @JsonValue
    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
