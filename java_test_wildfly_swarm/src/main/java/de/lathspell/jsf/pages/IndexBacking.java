package de.lathspell.jsf.pages;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import lombok.extern.slf4j.Slf4j;

@Named("indexBacking")
@ApplicationScoped
@Slf4j
public class IndexBacking implements Serializable {

    public IndexBacking() {
        log.info("ctor");
    }
    
    public String getTime() {
        log.info("returning date and time");
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
