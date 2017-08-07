package de.lathspell.test.model;

import lombok.Getter;

public enum UserType {
    OWNER(1),
    SITTER(2),
    BOTH(3),
    ADMIN(4);

    @Getter
    private final int code;

    private UserType(int code) {
        this.code = code;
    }

}
