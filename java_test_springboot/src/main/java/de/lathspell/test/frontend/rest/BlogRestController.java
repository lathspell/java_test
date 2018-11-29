package de.lathspell.test.frontend.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public List<Article> getArticlePages(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "9999") int size) {
        return articleRepo.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))).getContent();
    }
}
