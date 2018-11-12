package de.lathspell.test.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @GetMapping("/test1")
    public String test1() {
        log.info("entering test1()");
        return "/user/test1.html"; // defaults to just "test1.html"
    }

    @GetMapping("/test2")
    @PreAuthorize("hasRole('ADMIN')")
    public String test2() {
        log.info("entering test2()");
        return "/user/test2.html";
    }
}
