package de.lathspell.test.rest;

import lombok.extern.slf4j.Slf4j;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring REST Controller.
 *
 * The @RestController annotation includes @Controller which is a specialization of @Component and @ResponseBody which tells Spring that the return
 * value of every method should be converted into an HTTP Response.
 *
 */
@RestController
@RequestMapping("/rest/greeting")
@Slf4j
public class GreetingController {

    /**
     * Classic mapping, defaults to GET and TEXT_PLAIN. This maps to "/rest/greeting/" as no further path is specified. Query params like "?name=Tim"
     * are put into the argument "name".
     */
    @RequestMapping
    public String hello1(String name) {
        log.info("hello1({})", name);
        return "Hello " + name;
    }

    /**
     * Classic mapping with fine tuning. Query params like "?name=Tim" are put into the argument "name".
     */
    @RequestMapping(path = "/hello2", method = GET, produces = TEXT_PLAIN_VALUE)
    public String hello2(String name) {
        log.info("hello2({})", name);
        return "Hello " + name;
    }

    /**
     * New style mapping, same as the others. Query params like "?name=Tim" are put into the argument "name".
     */
    @GetMapping(path = "/hello3", produces = TEXT_PLAIN_VALUE)
    public String hello3(String name) {
        log.info("hello3({})", name);
        return "Hello " + name;
    }

    /**
     * Only matches if this header is set in the request.
     */
    @RequestMapping(path = "/hello4", method = GET, headers = "X-Override=true")
    public String helloX(String name) {
        log.info("hello4({})", name);
        return "Hello Mr. X";
    }

    /**
     * This one needs a path param for the argument "name".
     */
    @GetMapping(path = "/hello5/{name:[A-Z0-9]+}", produces = TEXT_PLAIN_VALUE)
    public String hello5(@PathVariable("name") String name) {
        log.info("hello5({})", name);
        return "Hello " + name;
    }
}
