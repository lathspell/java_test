package de.lathspell.test.springboot.model;

import lombok.Data;

@Data
public class Person {

    private String firstName;
    private String lastName;
    private int yearOfBirth;
    
    public void setYearOfBirth(int year) {
        if (year < 1900) {
            throw new IllegalArgumentException();
        }
        yearOfBirth = year;
    }

}
