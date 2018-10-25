package de.lathspell.test4;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy // proxyTargetClass is only honored if the target class actually implements an interface! Else always a CGLIB subclass is created!
public class Config {
}
