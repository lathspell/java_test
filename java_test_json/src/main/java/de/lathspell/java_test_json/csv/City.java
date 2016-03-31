package de.lathspell.java_test_json.csv;

import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonFormat.DEFAULT_TIMEZONE;
import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@JsonIgnoreProperties({"extrastuff"})
public class City {

    private String name;

    /** Example for mapping Strings to Enums. */
    private Country country;

    /** Example for specially formatted number. */
    @JsonDeserialize(using = PopulationDeserializer.class)
    private int population;

    /** Example for Date format and names with spaces. */
    @JsonProperty("Some Date")
    @JsonFormat(shape=STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Europe/Berlin")
    private Date someDate;

    @JsonIgnore
    private String ignoreMe;

    /** Setter that converts CSV String to our Enum type. */
    public void setCountry(String country) {
        this.country = Country.valueOf(country.toUpperCase());
    }
}
