package de.lathspell.java_test_ee6.jsf.controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.lathspell.java_test_ee6.jsf.model.BlogArticle;

@ManagedBean
@SessionScoped
public class BlogController {

    List<BlogArticle> blogArticleList = new ArrayList<BlogArticle>();
    BlogArticle blogArticle;

    /** Creates a new instance of PersonController */
    public BlogController() {
        blogArticle = new BlogArticle();
    }

    public String addBlogArticle() {
        blogArticleList.add(blogArticle);
        blogArticle = new BlogArticle();
        return "Success";
    }

    // Indirect Access
    public void setTitle(String title) {
        blogArticle.setTitle(title);
    }

    public String getTitle() {
        return blogArticle.getTitle();
    }

    public void setText(String text) {
        blogArticle.setText(text);
    }

    public String getText() {
        return blogArticle.getText();
    }

    // Getter and Setter Methods
    public List<BlogArticle> getBlogArticleList() {
        return blogArticleList;
    }

    public void setBlogArticleList(List<BlogArticle> blogArticleList) {
        this.blogArticleList = blogArticleList;
    }

    public BlogArticle getBlogArticle() {
        return blogArticle;
    }

    public void setBlogArticle(BlogArticle blogArticle) {
        this.blogArticle = blogArticle;
    }
}
