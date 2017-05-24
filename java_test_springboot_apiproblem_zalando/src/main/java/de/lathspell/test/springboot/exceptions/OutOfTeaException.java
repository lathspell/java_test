package de.lathspell.test.springboot.exceptions;

public class OutOfTeaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OutOfTeaException(String message) {
        super(message);
    }

}
