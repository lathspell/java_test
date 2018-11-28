package de.lathspell.test.frontend.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.lathspell.test.db.ArticleRepo;
import de.lathspell.test.model.Article;

@Controller
@RequestMapping("/blog")
@Slf4j
public class BlogController {

    @Autowired
    private ArticleRepo articleRepo;
    
    @GetMapping("/")
    public ModelAndView getArticles(ModelAndView mv) {
        Page<Article> page = articleRepo.myFindFirstOrderByCreatedAtDesc(PageRequest.of(0, 15));
        log.info("Found {} articles", page.getNumberOfElements());
        
        log.info("There are {} articles", articleRepo.count());
        
        mv.getModelMap().addAttribute("articles", page.getContent());
        mv.setViewName("blog/index");
        return mv;
    }
}
