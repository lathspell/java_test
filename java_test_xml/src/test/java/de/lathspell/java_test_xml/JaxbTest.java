package de.lathspell.java_test_xml;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import de.lathspell.java_test_xml.generated.BookType;
import de.lathspell.java_test_xml.generated.Collection;
import de.lathspell.java_test_xml.generated.ObjectFactory;
import de.lathspell.java_test_xml.generated.BookType.Promotion;
import de.lathspell.java_test_xml.generated.Collection.Books;

import static org.junit.Assert.assertNotNull;

/**
 * JAXB Tests.
 *
 * xjc -p de.lathspell.test.xml.jaxb.generated -d ../../../../ bookstore.xsd
 */
public class JaxbTest {

    @Test
    public void useAsClasses() throws Exception {
        //
        // Create object tree
        //
        Collection collection = new Collection();
        Books books = new Collection.Books();
        BookType book = new BookType();

        book.setItemId("307");
        book.setName("JAXB today and beyond");
        books.getBook().add(book);
        collection.setBooks(books);

        Promotion promotion = new BookType.Promotion();
        promotion.setDiscount("5% off regular price");
        book.setPromotion(promotion);

        //
        // Marshall to DOM
        //
        JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class.getPackage().getName());
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        marshaller.marshal(collection, document);

        // DOM to XML
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
        Assert.assertEquals(expectedXml.toString(), sw.toString());
    }
}
