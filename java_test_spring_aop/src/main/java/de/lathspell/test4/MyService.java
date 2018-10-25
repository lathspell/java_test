package de.lathspell.test4;

import org.springframework.stereotype.Service;

@Service
public class MyService {

    public int add(int a, int b) {
        return a + b;
    }

    public long negLong(long a) {
        return -a;
    }
}
