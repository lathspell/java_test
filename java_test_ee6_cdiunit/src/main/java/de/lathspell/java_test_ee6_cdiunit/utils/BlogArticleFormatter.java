package de.lathspell.java_test_ee6_cdiunit.utils;

import de.lathspell.java_test_ee6_cdiunit.model.BlogArticle;


public class BlogArticleFormatter {

    public String getExcerpt(BlogArticle article, int numChars) {
        return article.getText().substring(0, numChars) + " ...";
    }
}
