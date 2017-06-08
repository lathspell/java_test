package de.lathspell.test.springboot.exceptions;

import lombok.Getter;

public class OutOfTeaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Getter
    private final String myDetails;

    public OutOfTeaException(String message, String myDetails) {
        super(message);
        this.myDetails = myDetails;
    }

}
