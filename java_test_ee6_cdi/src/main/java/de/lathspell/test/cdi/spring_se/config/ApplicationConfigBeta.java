package de.lathspell.test.cdi.spring_se.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("beta")
@PropertySource("classpath:spring_se/beta.properties")
public class ApplicationConfigBeta {
}
