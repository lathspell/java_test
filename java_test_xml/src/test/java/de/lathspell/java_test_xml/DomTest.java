package de.lathspell.java_test_xml;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DomTest {

    /** Test ob man die DOM auch von Hand erzeugen kann.
     *
     * Die Valdierung mit der .xsd Datei funktionierte nicht richtig. Das setValidating() prüft
     * allerhöchstens auf XML Syntax aber nicht auf ungültige Elemente.
     * Von daher: Finger weg und jaxb benutzen!
     */
    @Test
    public void useAsDOM() throws Exception {
        // DOM erzeugen
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        /* Und was genau bewirkt folgendes? Scheinbar nämlich gar nichts.
        documentBuilderFactory.setNamespaceAware(true);
        documentBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                "http://www.w3.org/2001/XMLSchema");
        documentBuilderFactory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource",
                new InputSource(new FileInputStream(srcPath+"/bookstore.xsd")));
        */

        /* Irgendwie kann man das bestimmt auch aktivieren...
        File schemaFile = new File(srcPath+"/bookstore.xsd");
        Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaFile);
        documentBuilderFactory.setSchema(schema);
        */

        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element collection = document.createElement("Collection");
        document.appendChild(collection);

        Element books = document.createElement("books");
        collection.appendChild(books);

        Element book = document.createElement("book");
        Attr attr = document.createAttribute("itemId");
        attr.setValue("307");
        book.setAttributeNode(attr);
        books.appendChild(book);

        Element name = document.createElement("name");
        name.appendChild(document.createTextNode("JAXB today and beyond"));
        book.appendChild(name);

        Element isbn = document.createElement("ISBN");
        isbn.appendChild(document.createTextNode("0"));
        book.appendChild(isbn);

        Element promotion = document.createElement("promotion");
        Element discount = document.createElement("Discount");
        discount.appendChild(document.createTextNode("5% off regular price"));
        promotion.appendChild(discount);
        book.appendChild(promotion);

        // DOM zu XML
        StringWriter sw = new StringWriter();
        OutputFormat format = new OutputFormat(document, "UTF-8", true);
        new XMLSerializer(sw, format).serialize(document);
        Assert.assertTrue(sw.toString().length() > 100);

        // Check
        InputStream is = ClassLoader.getSystemResourceAsStream("de/lathspell/java_test_xml/bookstore.expected.xml");
        assertNotNull(is);
        BufferedReader bis = new BufferedReader(new InputStreamReader(is));
        StringBuilder expectedXml = new StringBuilder();
        String s;
        while ((s = bis.readLine()) != null) expectedXml.append(s+"\n");
        bis.close();

        assertEquals(expectedXml.toString(), sw.toString());
    }
}
