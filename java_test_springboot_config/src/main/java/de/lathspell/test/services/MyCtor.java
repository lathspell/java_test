package de.lathspell.test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MyCtor {

    private final String name;

    @Autowired
    public MyCtor(@Value("${app.my_ctor_param1}") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
