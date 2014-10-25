package de.lathspell.java_test_ee7_jsf.taglib;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("taglibBacking")
@SessionScoped
public class TaglibBacking implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(TaglibBacking.class);

    private Integer nr;

    private String nickname;

    public void submit() {
        log.info("submit");
    }

    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
