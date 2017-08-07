package de.lathspell.test.java_test_camel.model;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConverters;

@Slf4j
public class PersonConverter implements TypeConverters {

    @Converter
    public Person toPerson(String body, Exchange exchange) throws Exception {
        String name = exchange.getIn().getBody(String.class);

        Person p = new Person();
        p.setName(name);
        return p;
    }

}
