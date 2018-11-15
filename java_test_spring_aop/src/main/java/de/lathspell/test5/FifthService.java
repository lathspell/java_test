package de.lathspell.test5;

import org.springframework.stereotype.Service;

@Service
class FifthService {

    public int start(int a) {
        if (a == 3) {
            throw new RuntimeException("Why three?!");
        }
        return a * 2;
    }

}
