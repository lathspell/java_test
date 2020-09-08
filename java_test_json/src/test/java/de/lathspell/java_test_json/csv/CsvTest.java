package de.lathspell.java_test_json.csv;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;

public class CsvTest {

    @Ignore("FIXME")
    @Test
    public void csv2pojo() throws Exception {
        // columns are in different order as in POJO but that's fine!
        String csv = "country;name;Some Date;population;extrastuff\n"
                + "# no follows the first line (this is only a comment)\n"
                + "de;cologne;1923-04-08 11:22:33;1.046.680;famous for beer\n";

        CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';').withComments();
        MappingIterator<City> iter = new CsvMapper().readerFor(City.class).with(schema).readValues(csv);
        City c1 = iter.next();

        assertEquals(Country.DE, c1.getCountry());
        assertEquals("08.04.1923 11:22:33", DateFormat.getDateTimeInstance().format(c1.getSomeDate()));
    }

    @Test
    public void pojo2csv() throws Exception {
        City c1 = new City();
        c1.setCamelCase("foo");
        c1.setName("cologne");
        c1.setCountry("de");
        c1.setPopulation(1046680);
        c1.setSomeDate(Date.from(LocalDateTime.parse("1923-04-08T11:22:33.00").toInstant(ZoneOffset.of("+01:00"))));
        c1.setIgnoreMe("ignore me");
        CsvSchema schema2 = new CsvMapper().schemaFor(City.class).withHeader().withColumnSeparator(';');
        String csv2 = new CsvMapper().writerFor(City.class).with(schema2).writeValueAsString(c1);

        // Order is alphabetically but strictly speaking "undefined" unless @JsonPropertyOrder is used
        String expected2 = "\"Some Date\";camelCase;country;name;population\n"
                + "\"1923-04-08 11:22:33\";foo;DE;cologne;1046680\n";
        assertEquals(expected2, csv2);
    }

}
