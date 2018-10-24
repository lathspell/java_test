package de.lathspell.test2;

import org.springframework.stereotype.Service;

@Service
public class MyOtherServiceImpl implements MyOtherService {

    @Override
    public int sub(int a, int b) {
        return a - b;
    }
}
