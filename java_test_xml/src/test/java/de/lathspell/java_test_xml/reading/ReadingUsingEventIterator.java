package de.lathspell.java_test_xml.reading;

import java.io.*;
import javax.xml.stream.*;
import javax.xml.stream.events.*;

import org.junit.Test;

public class ReadingUsingEventIterator {
    private XMLEventReader xmlEventReader = null;

    @Test
    public void read() throws Exception {

        xmlEventReader = XMLInputFactory.newInstance().createXMLEventReader(new FileReader("src/main/java/de/lathspell/java_test_xml/reading/myCalendar.xml"));

        while (xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();

            if (xmlEvent.isStartElement()) {
                System.out.print(" <" + xmlEvent.asStartElement().getName() + "> ");
            } else if (xmlEvent.isCharacters()) {
                System.out.print(" " + xmlEvent.asCharacters().getData() + " ");
            } else if (xmlEvent.isEndElement()) {
                System.out.print(" </" + xmlEvent.asEndElement().getName() + "> ");
            }
        }

        xmlEventReader.close();
    }

}
