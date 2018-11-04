package de.lathspell.test.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring WebMVC configuration.
 * <p>
 * This class gets loaded by ServletConfiguration and sets up the Web framework
 * by adding resource paths and tell WebMVC where to find the template file for
 * the view names.
 *
 * @see ServletSecurityInit
 */
@Configuration
@ComponentScan("de.lathspell.test")
@EnableWebMvc // - to use WebMvcConfigurer
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

}
