package de.lathspell.test.lombok;

import java.io.IOException;
import lombok.SneakyThrows;

public class AtSneakyException {

    /**
     * This method may not declare any checked exception.
     * 
     * E.g. because it implements or overrides a method that has none declared.
     * Using the annotation it may lie though and throw one.
     */
    @SneakyThrows
    public void noCheckedExceptionFromMe() {
        throw new IOException("Now imagine a meaningfull reaction!");
    }
}
