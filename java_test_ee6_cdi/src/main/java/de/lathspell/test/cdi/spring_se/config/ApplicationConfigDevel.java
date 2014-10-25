package de.lathspell.test.cdi.spring_se.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("devel")
@PropertySource("classpath:spring_se/devel.properties")
public class ApplicationConfigDevel {
}
