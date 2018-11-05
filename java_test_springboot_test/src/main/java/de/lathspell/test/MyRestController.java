package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

@RestController
@Slf4j
public class MyRestController {

    @GetMapping("/test1")
    public String test1() {
        return "This is test1!";
    }

    @GetMapping("/bad-request")
    public String clientError(HttpServletResponse response) {
        response.setStatus(SC_BAD_REQUEST);
        return "Stupid client!";
    }

    @GetMapping("/exception")
    public String exception() {
        throw new RuntimeException("Bad things happen.");
    }
}
