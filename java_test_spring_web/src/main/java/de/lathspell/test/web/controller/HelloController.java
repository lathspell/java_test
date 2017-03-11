package de.lathspell.test.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(value = "/world", method = GET)
    public String helloWorld(Model model) {
        model.addAttribute("name", "World");
        return "hello";
    }

    /** The argument's name "name" is the same as that of the path variable and therefore recognized. */
    @RequestMapping(value = "/to/{name: .*}", method = GET)
    public String helloToName(String name, Model model) {
        model.addAttribute("name", "World");
        return "hello";
    }
    
    /** The argument's name "name" is the same as that of the path variable and therefore recognized. */
    @RequestMapping(value = "/lf/{name: .*}", method = GET)
    public String helloToName(String name, Model model) {
        model.addAttribute("name", "World");
        return "hello";
    }
}
