package de.lathspell.test.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class MyWebController {

    @GetMapping("/test1")
    public void test1() {
        log.info("entering test1()");
        // uses src/main/resources/templates/test1.html
    }

    @GetMapping("/test2")
    public String test2(@RequestParam(required = false, defaultValue = "anonymous") String name, Model model) {
        log.info("entering test2({})", name);
        model.addAttribute("name", name);
        return "test2"; // // uses src/main/resources/templates/test2.html
    }
}

