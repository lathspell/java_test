package de.lathspell.test.frontend.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public ModelAndView index(ModelAndView mv) {
        log.info("Serving index");
        mv.setViewName("index");
        return mv;
    }

}
