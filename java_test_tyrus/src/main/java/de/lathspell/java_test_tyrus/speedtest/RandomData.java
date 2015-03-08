package de.lathspell.java_test_tyrus.speedtest;

import java.security.SecureRandom;

public class RandomData {

    public static final byte[] data;

    static {
        data = new byte[1024 * 1024 * 3];
        new SecureRandom().nextBytes(data);
    }

}
