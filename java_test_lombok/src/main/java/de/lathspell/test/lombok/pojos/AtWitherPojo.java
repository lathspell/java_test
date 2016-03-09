package de.lathspell.test.lombok.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Wither;

/**
 * BEWARE: This is experimental.
 * 
 * The withAge(int i) method does not set this objects variable age nor does it
 * return the current object. Instead it creates a new instance with just the
 * value of "age" changed!
 * 
 * Personally, I didn't expect that and do not like it.
 * 
 */
@AllArgsConstructor
@Getter
public class AtWitherPojo {

    // The "private" are not necessary but I like it.

    @Wither
    private final String name;
    
    @Wither
    private final int age;
}
