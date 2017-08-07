package de.lathspell.test.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private String firstName;
    private String lastName;
    private LocalDate birthday;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
