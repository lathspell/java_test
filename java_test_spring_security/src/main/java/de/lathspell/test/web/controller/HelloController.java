package de.lathspell.test.web.controller;

import lombok.extern.slf4j.Slf4j;
import static org.springframework.http.HttpStatus.NOT_MODIFIED;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/hello")
@Slf4j
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

    @RequestMapping("/use-freemarker")
    public String useFreemarker(ModelMap map) {
        log.info("useFreemarker()");
        map.put("name", "Freemarker");
        return "use-freemarker";
    }

    @RequestMapping("/use-thymeleaf")
    public String useThymeleaf(ModelMap map) {
        log.info("useThymeleaf()");
        map.put("name", "Thymeleaf");
        return "use-thymeleaf.html"; // using ".html" to trigger setViewNames() in the thymeleafViewResolver
    }

    /**
     * Returning a ModelAndView instead of the name of the View is discouraged as it couples to tightly.
     */
    @RequestMapping("/other")
    public ModelAndView other() {
        log.info("other()");
        ModelAndView mv = new ModelAndView("hello_other");
        mv.addObject("name", "Other Guy");
        return mv;
    }

    /** This creates a model attribute that is accessible as ${prop} or similar in the view template. */
    @ModelAttribute("prop")
    private String prop() {
        return "*PROPPY*";
    }

    /**
     * Specifying a HTTP 304 "Not Modified" response code.
     */
    @RequestMapping("/not-modified")
    @ResponseStatus(NOT_MODIFIED)
    public void notModified() {
        log.info("notModified()");
    }

}
