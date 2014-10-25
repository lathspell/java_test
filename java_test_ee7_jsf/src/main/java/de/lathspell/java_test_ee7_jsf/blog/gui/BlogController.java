package de.lathspell.java_test_ee7_jsf.blog.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import de.lathspell.java_test_ee7_jsf.blog.model.BlogArticle;

/**
 * JSF Backing Bean for BlogArticle.
 */
@Named("blogController")
@SessionScoped
public class BlogController implements Serializable {

    List<BlogArticle> blogArticleList = new ArrayList<>(); // init here...

    BlogArticle blogArticle;

    public BlogController() {
        blogArticle = new BlogArticle(); // ... or init here
    }

    /**
     * To let the XHTML access blogArticle only indirectly (setter).
     */
    public void setText(String text) {
        blogArticle.setText(text);
    }

    /**
     * To let the XHTML access blogArticle only indirectly (getter).
     */
    public String getText() {
        return blogArticle.getText();
    }

    /**
     * To let the XHTML access blogController.blogArticle directly.
     */
    public BlogArticle getBlogArticle() {
        return blogArticle;
    }

    /**
     * Retrieves the list of articles for the dataTable.
     */
    public List<BlogArticle> getBlogArticleList() {
        return blogArticleList;
    }

    /**
     * Adds a new article to the dataTable when the commandButton is pressed.
     */
    public String addBlogArticle() {
        blogArticleList.add(blogArticle);
        blogArticle = new BlogArticle();
        return null; // stay on same page
    }
}
