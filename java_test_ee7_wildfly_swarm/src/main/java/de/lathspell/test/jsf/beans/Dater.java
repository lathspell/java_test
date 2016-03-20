package de.lathspell.test.jsf.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import lombok.extern.slf4j.Slf4j;

@Named
@RequestScoped
@Slf4j
public class Dater {

    public String getCurrentDate() {
        String time = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        log.info("Now is " + time);
        return time;
    }
}
