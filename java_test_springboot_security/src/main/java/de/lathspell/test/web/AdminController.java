package de.lathspell.test.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @GetMapping("/test1")
    public String test1() {
        log.info("entering admin test1()");
        return "/admin/test1.html";
    }

}

