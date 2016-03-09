package de.lathspell.test.lombok.pojos;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class AtFluentDataPojo {

    // The "private" are not necessary but I like it.
    /**
     * This field is marked as required and thus part of the generated ctor.
     */
    private final String name;
    private int age;
}
