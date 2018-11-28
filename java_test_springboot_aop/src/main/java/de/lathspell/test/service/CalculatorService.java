package de.lathspell.test.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public int add(int a, int b) {
        return a + b;
    }

    public int div(int a, int b) {
        return a / b;
    }

    public int strangeDiv(int a, int b) {
        return a / b;
    }
}
