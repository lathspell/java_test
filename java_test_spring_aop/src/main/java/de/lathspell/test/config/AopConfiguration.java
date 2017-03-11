package de.lathspell.test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "de.lathspell.test")
@EnableAspectJAutoProxy
public class AopConfiguration {

}
