package de.lathspell.test.frontend.web;

import de.lathspell.test.model.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private MyUser myUser;

    @GetMapping("/")
    public ModelAndView index(ModelAndView mv) {
        log.info("Serving index with myUser={}", myUser);
        mv.getModel().put("myUser", myUser);
        mv.setViewName("index");
        return mv;
    }

}
