package de.lathspell.test.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MyCtor {

    @Getter
    private final String name;

    @Autowired
    public MyCtor(@Value("${app.myCtor_param1}") String name) {
        this.name = name;
    }

}
