package de.lathspell.test.rest;

import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * Default Mapper.
     *
     * Without parameters this is the default mapper for all HTTP verbs
     * (GET, PUT, POST, etc.) and all URLs that have no more-specific mapping.
     */
    @RequestMapping
    public String hello1(String name) {
        return "Hello1 " + name;
    }

    /**
     * Mapping with fine tuning.
     */
    @RequestMapping(path = "/hello2", method = GET, produces = TEXT_PLAIN_VALUE)
    public String hello2(String name) {
        return "Hello2 " + name;
    }

    /**
     * Mapping using the new annotation name.
     */
    @GetMapping(path = "/hello3", produces = TEXT_PLAIN_VALUE)
    public String hello3(String name) {
        return "Hello3 " + name;
    }

    /**
     * Only matches if this header is set in the request.
     *
     * (apart from that it works for every URL as no "path" is specified)
     *
     * <pre>
     * $ curl -H 'X-Override: true' 'http://localhost:8090/java_test_spring_rest/whatever?name=Tim'
     * HelloX Tim
     * </pre>
     */
    @RequestMapping(method = GET, headers = "X-Override=true")
    public String helloX(@RequestParam("name") String name) {
        return "HelloX " + name;
    }

    /**
     * HTTP POST example.
     *
     * - the @RequestBody is mandatory!
     *
     * <pre>
     * $ curl -d '{"name":"Tim"}' -H 'Content-Type: application/json' -X POST 'http://localhost:8090/java_test_spring_rest/uppercase?name=foo'
     * {"NAME":"TIM"}
     * </pre>
     *
     */
    @PostMapping(path = "uppercase", consumes = TEXT_PLAIN_VALUE)
    public String uppercase(@RequestBody String input) {
        Assert.notNull(input, "Body darf nicht NULL sein!");
        return input.toUpperCase();
    }

}
