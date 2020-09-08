package de.lathspell.java_test_json.csv;

import java.util.Date;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
//@AllArgsConstructor
@JsonIgnoreProperties({"extrastuff"})
public class City {

    private String camelCase;

    private String name;

    /** Example for mapping Strings to Enums. */
    private Country country;

    /** Example for specially formatted number. */
    @JsonDeserialize(using = PopulationDeserializer.class)
    private int population;

    /** Example for Date format and names with spaces. */
    @JsonProperty("Some Date")
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Berlin")
    private Date someDate;

    @JsonIgnore
    private String ignoreMe;

    /** Setter that converts CSV String to our Enum type. */
    public void setCountry(String country) {
        this.country = Country.valueOf(country.toUpperCase());
    }
}
