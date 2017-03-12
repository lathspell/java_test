package de.lathspell.test.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping
    public String index() {
        log.info("index() => hello");
        return "hello";
    }

    @RequestMapping("/{name}")
    public String helloName(@PathVariable("name") String name, ModelMap map) {
        log.info("helloName({}) => hello", name);
        map.put("name", name);
        return "hello";
    }

}
