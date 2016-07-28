package de.lathspell.test.io.serializable;

import java.io.Serializable;

/**
 * Serialisierbarer Datentyp der in der Klasse Bunny benutzt wird.
 * 
 * @see Bunny
 */
class BunnySize implements Serializable {

    private static final long serialVersionUID = -3299036643949386127L;
    private final int size;

    public BunnySize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return String.valueOf(size);
    }
    
}
