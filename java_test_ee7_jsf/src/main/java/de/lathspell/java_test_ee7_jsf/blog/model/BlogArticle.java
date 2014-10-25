package de.lathspell.java_test_ee7_jsf.blog.model;

import javax.validation.constraints.NotNull;

import de.lathspell.java_test_ee7_jsf.blog.validators.BlogTitle;

public class BlogArticle {

    @BlogTitle
    private String title;

    @NotNull
    private String text;

    public BlogArticle() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
