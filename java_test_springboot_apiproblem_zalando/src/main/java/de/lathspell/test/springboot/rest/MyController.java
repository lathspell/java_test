package de.lathspell.test.springboot.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.lathspell.test.springboot.exceptions.OutOfTeaException;

@RestController
@RequestMapping("/my")
public class MyController {

    @GetMapping("/hello")
    public String name(@RequestParam(name = "name", required = true) String name) {
        return "Hello " + name;
    }

    @GetMapping("/tea")
    public void getTea() {
        throw new OutOfTeaException("No tea left!", "Some ugly details...");
    }

    @GetMapping("/crash")
    public void crash() {
        throw new RuntimeException("Kaboom!");
    }
}
