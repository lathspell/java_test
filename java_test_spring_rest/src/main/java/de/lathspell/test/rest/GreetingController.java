package de.lathspell.test.rest;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import org.springframework.web.bind.annotation.GetMapping;
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
public class GreetingController {

    /** Classic mapping, defaults to GET and TEXT_PLAIN. */
    @RequestMapping
    public String hello1(String name) {
        return "Hello " + name;
    }
    
    /** Classic mapping with fine tuning. */
    @RequestMapping(method = GET, produces = TEXT_PLAIN_VALUE)
    public String hello2(String name) {
        return "Hello " + name;
    }
    
    /** New style mapping, same as the others. */
    @GetMapping(produces = TEXT_PLAIN_VALUE)
    public String hello3(String name) {
        return "Hello " + name;
    }

    /** Only matches if this header is set in the request. */
    @RequestMapping(method = GET, headers = "X-Override=true")
    public String helloX(String name) {
        return "Hello Mr. X";
    }
}
