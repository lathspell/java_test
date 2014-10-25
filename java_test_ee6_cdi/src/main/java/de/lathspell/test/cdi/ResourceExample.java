package de.lathspell.test.cdi;

import javax.annotation.Resource;

public class ResourceExample {

    @Resource(name = "myFirstColour")
    String myFirstColour;
}
