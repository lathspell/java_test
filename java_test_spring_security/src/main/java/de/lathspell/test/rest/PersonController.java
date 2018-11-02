package de.lathspell.test.rest;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring REST Controller.
 *
 * The @RestController annotation includes @Controller which is a specialization of @Component and @ResponseBody which tells Spring that the return
 * value of every method should be converted into an HTTP Response.
 *
 */
@RestController
@RequestMapping("/rest/person")
@Slf4j
public class PersonController {

    @Data
    static class Person {

        private String firstName;
        private String lastName;
        private int age;
    }

    private final List<Person> list = new ArrayList<>();

    /**
     * Classic mapping, defaults to GET and TEXT_PLAIN.
     *
     * This maps to "/rest/greeting/" as no further path is specified.
     * Query params like "?name=Tim" are put into the argument "name".
     *
     * <pre>curl -H "Content-Type: application/json" -X POST -d '{"firstName": "Tim", "lastName": "Tu" }' http://localhost:8090/java_test_spring_webmvc/rest/person</pre>
     */
    @RequestMapping(produces = APPLICATION_JSON_VALUE)
    public List<Person> getAll() throws JsonProcessingException {
        log.info("getAll()");
        ObjectMapper om = new ObjectMapper();
        om.writeValueAsString(list);
        return list;
    }

    /**
     * Creates an entry.
     *
     * <pre>curl -H "Content-Type: application/json" -X POST -d '{"firstName": "Tim", "lastName": "Tu" }' http://localhost:8090/java_test_spring_webmvc/rest/person</pre>
     */
    @PostMapping
    public void postPerson(@RequestBody Person p) {
        log.info("postPerson with: " + p);
        list.add(p);
    }
}
