package de.lathspell.test.lombok.pojos;

import lombok.Value;

@Value
public class AtValuePojo {

    // The "private" are not necessary but I like it.
    private String name;
    private int age;

}
