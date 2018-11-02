package de.lathspell.test.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring WebMVC configuration.
 *
 * This class gets loaded by ServletConfiguration and sets up the Web framework
 * by adding resource paths and tell WebMVC where to find the template file for
 * the view names.
 *
 * @see ServletConfiguration
 */
@Configuration
@ComponentScan("de.lathspell.test")
@EnableWebMvc
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/*");
    }

}
