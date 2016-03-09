package de.lathspell.test.lombok.pojos;

import lombok.Data;

@Data
public class AtDataPojo {

    // The "private" are not necessary but I like it.
    /**
     * This field is marked as required and thus part of the generated ctor.
     */
    private final String name;
    private int age;
}
