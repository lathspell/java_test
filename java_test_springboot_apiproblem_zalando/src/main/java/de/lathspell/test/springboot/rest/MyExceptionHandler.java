package de.lathspell.test.springboot.rest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

/**
 * That's all what's needed to activate the Zalando exception handler that returns application/problem+json.
 */
@ControllerAdvice
public class MyExceptionHandler implements ProblemHandling {

}
