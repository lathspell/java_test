package de.lathspell.test.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.TEXT_HTML_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

/**
 * Web Controller.
 *
 * Caveat: Spring has no builtin Integer MessageConverter so returning "int" gives
 * an Exception from the MessageConverter. Thus we return Strings.
 */
@RestController
@RequestMapping("/rest/calc")
//@PreAuthorize("calc")
@Slf4j
public class CalcController {

    @GetMapping(path = "/add")
    public String add(@RequestParam int a, @RequestParam int b) {
        log.info("entering add()");
        return String.valueOf(a + b);
    }

    @GetMapping(path = "/mul")
    //  @PreAuthorize("calc_mul")
    public String mul(@RequestParam int a, @RequestParam int b) {
        log.info("entering mul()");
        return String.valueOf(a * b);
    }
}
