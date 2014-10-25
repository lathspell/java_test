package de.lathspell.java_test_xml;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Test;

@XmlRootElement()
class Person {

    private String name;

    private int age;

    private Calendar dateOfBirth;

    private String type;

    public Person() {
    }

    public Person(String name, int age, Calendar dateOfBirth, String type) {
        this.name = name;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.type = type;
    }

    @XmlElement()
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement()
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @XmlElement()
    public Calendar getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Calendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @XmlAttribute()
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

public class JaxbWithAnnotationsTest {

    @Test
    public void MakingXml() throws Exception {
        JAXBContext context = JAXBContext.newInstance(Person.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        String soll
                = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<person type=\"employee\">\n"
                + "    <age>32</age>\n"
                + "    <dateOfBirth>1970-02-10T00:00:00+01:00</dateOfBirth>\n"
                + "    <name>Anonymous</name>\n"
                + "</person>\n";

        StringWriter sw = new StringWriter();
        Person person = new Person("Anonymous", 32, new GregorianCalendar(1970, 1, 10), "employee");
        marshaller.marshal(person, sw);

        assertEquals(sw.toString(), soll);
    }
}
