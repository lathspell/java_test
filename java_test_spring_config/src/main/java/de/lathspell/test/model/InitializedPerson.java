package de.lathspell.test.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class InitializedPerson {

    private String firstName;
    private String lastName;
    private LocalDate birthday;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    /** For Spring "init-method". */
    private void initMethod() {
        log.info("Entering initMethod");
        lastName = lastName.toUpperCase();
    }
}
