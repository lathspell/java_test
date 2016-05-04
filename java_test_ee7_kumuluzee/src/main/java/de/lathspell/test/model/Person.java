package de.lathspell.test.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Simple custom class to test JSON encoding and decoding. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    public static enum Sex {

        UNKNOWN(0),
        MALE(1),
        FEMALE(2),
        NA(9);

        private final int code;

        private Sex(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
    public static final long DATE_1970_01_31 = 2588400000L;

    public static final Person MAX = new Person("Max", "Mustermann", Person.Sex.MALE, new Date(DATE_1970_01_31), "brown", 176);

    public static final String MAX_JSON = "{\"firstName\":\"Max\",\"lastName\":\"Mustermann\",\"sex\":\"MALE\",\"dayOfBirth\":" + DATE_1970_01_31 + ",\"hairColor\":\"brown\",\"height\":176}";

    private String firstName;

    private String lastName;

    private Sex sex;

    private Date dayOfBirth;

    private String hairColor;

    private Integer height;

}
