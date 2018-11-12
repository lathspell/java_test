package de.lathspell.test;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .mvcMatchers("/rest/**").hasRole("MACHINE")
                .mvcMatchers("/user/**").hasRole("USER")
                .anyRequest().permitAll().and()
                .formLogin().permitAll().and()
                .logout().logoutSuccessUrl("/").deleteCookies("JSESSIONID").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}admin").roles("USER", "MACHINE", "ADMIN").and()
                .withUser("user").password("{noop}user").roles("USER").and()
                .withUser("machine").password("{noop}machine").roles("MACHINE");
    }
}
