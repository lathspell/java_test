package de.lathspell.test.lombok.pojos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AtValueBuilderPojo {

    // The "private" are not necessary but I like it.
    /**
     * This field is marked as required and thus part of the generated ctor.
     */
    private final @lombok.NonNull String name;
    private final int age;
}
