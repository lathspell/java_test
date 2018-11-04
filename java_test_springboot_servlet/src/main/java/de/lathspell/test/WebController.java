package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class WebController {

    @GetMapping("/test1")
    public void test1() {
        log.info("entering test1");
    }
}
