package de.lathspell.test.frontend.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.lathspell.test.db.ArticleRepo;
import de.lathspell.test.model.Article;

@RestController
@RequestMapping("/rest/blog")
public class BlogRestController {

    @Autowired
    private ArticleRepo articleRepo;

    @GetMapping("/articles")
    public List<Article> getArticlePages(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "0") int size) {
        return articleRepo.findAll(Sort.by(Order.desc("createdAt")), PageRequest.of(page, size)).getContent();
    }
}
