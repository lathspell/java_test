package de.lathspell.test.frontend.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.lathspell.test.model.Article;

@Controller
@RequestMapping("/edit")
@Slf4j
public class EditController {

    @GetMapping("/")
    public ModelAndView newArticleForm(ModelAndView mv) {
        mv.getModelMap().addAttribute("article", new Article());
        mv.setViewName("edit/article");        
        return mv;
    }

    @PostMapping("/submit")
    public ModelAndView articleSubmit(Article article, ModelAndView mv) {
        log.info("Submit got {}", article);
        mv.getModel().put("msg", "Article saved!");
        mv.setViewName("edit/article");
        return mv;
    }

}
