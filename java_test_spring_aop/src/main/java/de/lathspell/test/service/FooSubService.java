package de.lathspell.test.service;

import org.springframework.stereotype.Service;

@Service
public class FooSubService extends FooService {

    void callProtected() {
        giveMeProtected();
    }

}
