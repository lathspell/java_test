package de.lathspell.java_test_ee7_stack1;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named("formBacking")
@SessionScoped
public class FormBacking implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(FormBacking.class);

    private String nickname;

    public String submit() {
        log.info("Form submitted with nickname=" + nickname);

        return null;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
