package de.lathspell.java_test_json;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static org.junit.Assert.assertEquals;

/**
 * Testing conversion to and from the new java.time.* types.
 */
public class Jsr310LocalDateTest {

    @Data
    @AllArgsConstructor
    @JsonPropertyOrder({"localDateString", "localDateTimeString", "legacyDateString", "localDate", "localDateTime", "legacyDate"})
    private class Foo {

        @JsonFormat(shape = STRING)
        private LocalDate localDateString;
        @JsonFormat(shape = STRING)
        private LocalDateTime localDateTimeString;
        @JsonFormat(shape = STRING)
        private Date legacyDateString;

        private LocalDate localDate;
        private LocalDateTime localDateTime;
        private Date legacyDate;
    }

    private final Foo foo = new Foo(
            LocalDate.of(2016, 4, 8),
            LocalDateTime.of(2016, 4, 8, 16, 35, 42),
            Date.from(Instant.parse("2016-04-08T16:36:42Z")),
            LocalDate.of(2016, 4, 8),
            LocalDateTime.of(2016, 4, 8, 16, 35, 42),
            Date.from(Instant.parse("2016-04-08T16:36:42Z")));

    @Test
    public void testJavaTimeModule() throws Exception {
        String expected
                = "{\n"
                + "  \"localDate\" : [ 2016, 4, 8 ],\n"
                + "  \"localDateTime\" : [ 2016, 4, 8, 16, 35, 42 ],\n"
                + "  \"legacyDate\" : 1460133402000,\n"
                + "  \"localDateString\" : \"2016-04-08\",\n"
                + "  \"localDateTimeString\" : \"2016-04-08T16:35:42\",\n"
                + "  \"legacyDateString\" : \"2016-04-08T16:36:42.000+0000\"\n"
                + "}";
        String actual = new ObjectMapper().enable(INDENT_OUTPUT).registerModule(new JavaTimeModule()).writeValueAsString(foo);
        assertEquals(expected, actual);
    }

    @Test
    public void testStandardDate() throws Exception {
        String expected
                = "{\n"
                + "  \"localDateString\" : {\n"
                + "    \"year\" : 2016,\n"
                + "    \"month\" : \"APRIL\",\n"
                + "    \"chronology\" : {\n"
                + "      \"calendarType\" : \"iso8601\",\n"
                + "      \"id\" : \"ISO\"\n"
                + "    },\n"
                + "    \"era\" : \"CE\",\n"
                + "    \"monthValue\" : 4,\n"
                + "    \"dayOfMonth\" : 8,\n"
                + "    \"dayOfYear\" : 99,\n"
                + "    \"dayOfWeek\" : \"FRIDAY\",\n"
                + "    \"leapYear\" : true\n"
                + "  },\n"
                + "  \"localDateTimeString\" : {\n"
                + "    \"year\" : 2016,\n"
                + "    \"monthValue\" : 4,\n"
                + "    \"month\" : \"APRIL\",\n"
                + "    \"dayOfMonth\" : 8,\n"
                + "    \"dayOfYear\" : 99,\n"
                + "    \"dayOfWeek\" : \"FRIDAY\",\n"
                + "    \"nano\" : 0,\n"
                + "    \"hour\" : 16,\n"
                + "    \"minute\" : 35,\n"
                + "    \"second\" : 42,\n"
                + "    \"chronology\" : {\n"
                + "      \"calendarType\" : \"iso8601\",\n"
                + "      \"id\" : \"ISO\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"legacyDateString\" : \"2016-04-08T16:36:42.000+0000\",\n"
                + "  \"localDate\" : {\n"
                + "    \"year\" : 2016,\n"
                + "    \"month\" : \"APRIL\",\n"
                + "    \"chronology\" : {\n"
                + "      \"calendarType\" : \"iso8601\",\n"
                + "      \"id\" : \"ISO\"\n"
                + "    },\n"
                + "    \"era\" : \"CE\",\n"
                + "    \"monthValue\" : 4,\n"
                + "    \"dayOfMonth\" : 8,\n"
                + "    \"dayOfYear\" : 99,\n"
                + "    \"dayOfWeek\" : \"FRIDAY\",\n"
                + "    \"leapYear\" : true\n"
                + "  },\n"
                + "  \"localDateTime\" : {\n"
                + "    \"year\" : 2016,\n"
                + "    \"monthValue\" : 4,\n"
                + "    \"month\" : \"APRIL\",\n"
                + "    \"dayOfMonth\" : 8,\n"
                + "    \"dayOfYear\" : 99,\n"
                + "    \"dayOfWeek\" : \"FRIDAY\",\n"
                + "    \"nano\" : 0,\n"
                + "    \"hour\" : 16,\n"
                + "    \"minute\" : 35,\n"
                + "    \"second\" : 42,\n"
                + "    \"chronology\" : {\n"
                + "      \"calendarType\" : \"iso8601\",\n"
                + "      \"id\" : \"ISO\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"legacyDate\" : 1460133402000\n"
                + "}";

        String actual = new ObjectMapper().enable(INDENT_OUTPUT).writeValueAsString(foo);
        assertEquals(expected, actual);
    }

}
