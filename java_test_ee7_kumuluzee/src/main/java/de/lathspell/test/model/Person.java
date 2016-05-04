package de.lathspell.test.model;

import java.util.Date;

/** Simple custom class to test JSON encoding and decoding. */
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

    public Person() {

    }

    public Person(String firstName, String lastName, Sex sex, Date dayOfBirth, String hairColor, Integer height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.dayOfBirth = dayOfBirth;
        this.hairColor = hairColor;
        this.height = height;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(Date dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
