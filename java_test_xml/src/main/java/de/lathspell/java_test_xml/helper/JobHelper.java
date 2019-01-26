package de.lathspell.java_test_xml.helper;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.LinkedList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import de.lathspell.java_test_xml.model.Job;

public class JobHelper {

    public static void validate(final String xml) throws Exception {

        // Insert DOCTYPE for validation and remote standalone
        LinkedList<String> lines = new LinkedList<>(Arrays.asList(xml.split("\n")));
        lines.set(0, lines.get(0).replaceFirst(" standalone=\"yes\"", ""));
        lines.add(1, "<!DOCTYPE job SYSTEM \"src/main/dtd/job.dtd\">");
        final String xml2 = StringUtils.join(lines, "\n");

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setValidating(true);

        DocumentBuilder builder = domFactory.newDocumentBuilder();
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void error(SAXParseException e) throws SAXException {
                throw new SAXException("Error validating:\n" + xml2, e);
            }

            @Override
            public void fatalError(SAXParseException e) throws SAXException {
                throw new SAXException("Fatal error validating:\n" + xml2, e);
            }

            @Override
            public void warning(SAXParseException e) throws SAXException {
                throw new SAXException("Warning validating:\n" + xml2, e);
            }
        });


        // Validate!
        builder.parse(new ByteArrayInputStream(xml2.getBytes()));
    }

    public static String toXml(final Job job) throws Exception {
        String packageName = Job.class.getPackage().getName();
        JAXBContext jc = JAXBContext.newInstance(packageName);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");

        StringWriter sw = new StringWriter();
        marshaller.marshal(job, sw);
        String xml = sw.toString();

        validate(xml);

        return xml;
    }
}
