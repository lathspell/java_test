package de.lathspell.test.config;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
@Slf4j
public class MessageConfiguration {

    @Bean
    public LocaleResolver localeResolver() {
        log.info("Creating LocaleResolver");
        
        return new LocaleResolver() {
            @Override
            public Locale resolveLocale(HttpServletRequest request) {
                log.debug("entering");
                Locale myLocale = (Locale) request.getSession().getAttribute("myLocale");
                log.debug("Found locale: {}", myLocale);
                return (myLocale == null ? Locale.ENGLISH : myLocale);
            }

            @Override
            public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
                throw new RuntimeException("Not implemented, who's calling?");
            }
        };
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource src = new ReloadableResourceBundleMessageSource();
        src.setBasename("WEB-INF/messages");
        src.setDefaultEncoding("UTF-8");
        return src;
    }
}
