package de.lathspell.test.web.controller;

import java.nio.file.FileSystemException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_MODIFIED;

import de.lathspell.test.model.Person;

@Controller
@RequestMapping("/hello")
@Slf4j
public class HelloController {

    /** This gets executed before any RequestMapping method. */
    @ModelAttribute
    public void populateModel(Model model) {
        model.addAttribute("myClass", this.getClass().getName());
    }

    @RequestMapping
    public String index() {
        log.info("index() => hello");
        return "hello";
    }

    @RequestMapping("/to-name/{name}")
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

    /**
     * Figuring out what Model is good for.
     *
     * Apparently it can hold key-value information for the view (e.g. JSP), similar to MpdelMap.
     *
     * Note that @PathVariable does not need to have an explicitly set name if it is equal to the variable name!
     */
    @GetMapping(path = "/with-model")
    public String hello6(Model model) {
        log.info("with-model()");
        model.addAttribute("foo", "bar");
        return "with-model";
    }

    /** ModelAndView is a convenience thing to return parameters, view name and HTTP status in one. */
    @GetMapping(path = "/hello7")
    public ModelAndView hello7(ModelAndView modelAndView) {
        log.info("with-modelandview()");
        modelAndView.addObject("foo", "bar");
        modelAndView.setStatus(HttpStatus.OK);
        modelAndView.setViewName("with-modelandview");
        return modelAndView;
    }

    /** Spring knows all kinds of classes that are automatically inserted. */
    @GetMapping(path = "/hello8")
    public void hello8(WebRequest webRequest, NativeWebRequest nativeWebRequest, HttpMethod method, Model model) {
        log.info("hello8()");

        model.addAttribute("webrequest_user", webRequest.getRemoteUser());
        model.addAttribute("nativewebrequest_method", ((HttpServletRequest) nativeWebRequest.getNativeRequest()).getMethod());
        model.addAttribute("httpmethod", method.toString());

        // Return type "void" implicitly maps "GET /hello/hello8" -> "/WEB-INF/views/hello/hello8.jsp"
    }

    @GetMapping(path = "/hello9", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    @ResponseBody
    public Person hello9() {
        return new Person("Tim", LocalDate.of(1977, 4, 23));
    }

    @GetMapping(path = "/hello10", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> hello10() {
        Person p = new Person("Tim", LocalDate.of(1977, 4, 23));
        return ResponseEntity.status(HttpStatus.OK).body(p);
    }

    /** See handle(). */
    @GetMapping(path = "/hello11")
    public void hello11() throws Exception {
        throw new FileSystemException("The file is not there!");
    }

    @ExceptionHandler({FileSystemException.class})
    public ResponseEntity<String> handle(Exception e) {
        return ResponseEntity.status(HttpStatus.OK).body("Handling exception: " + e.getMessage());
    }

}
