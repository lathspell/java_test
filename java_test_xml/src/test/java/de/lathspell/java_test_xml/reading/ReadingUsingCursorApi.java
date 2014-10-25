package de.lathspell.java_test_xml.reading;

import java.io.*;
import javax.xml.stream.*;
import javax.xml.stream.events.*;

public class ReadingUsingCursorApi {

    public static void main(String args[]) throws Exception {
        XMLStreamReader xmlReader;

        xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(new FileReader(
                "src/main/java/de/lathspell/java_test_xml/reading/myCalendar.xml"));

        while (xmlReader.hasNext()) {
            Integer eventType = xmlReader.next();

            switch (eventType) {
            case XMLEvent.START_ELEMENT:
                System.out.print(" <" + xmlReader.getName() + "> "); // Elements
                break;
            case XMLEvent.CHARACTERS:
                System.out.print(" " + xmlReader.getText() + " "); // XML Whitespaces und Text
                break;
            case XMLEvent.ATTRIBUTE:
                System.out.print(" @" + xmlReader.getName() + "@ ");
                break;
            case XMLEvent.END_ELEMENT:
                System.out.print(" </" + xmlReader.getName() + "> ");
                break;
            }
        }
        xmlReader.close();
    }

}
