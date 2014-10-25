package de.lathspell.java_test_xml;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import static org.junit.Assert.assertTrue;

public class DomToXmlTest {
    private String xml;

    @Before
    public void init() {
        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>  "
                + "<catalog xmlns:journal=\"http://www.w3.org/2001/XMLSchema-Instance\" >  "
                + "  <journal:journal title=\"XML\"  publisher=\"IBM developerWorks\">  "
                + "      <article journal:level=\"Intermediate\"               "
                + "            date=\"February-2003\">    "
                + "         <title>Design XML Schemas Using UML</title>  "
                + "         <author>Ayesha Malik</author>   "
                + "      </article> "
                + "  </journal:journal>  "
                + "  <journal title=\"Java Technology\"  publisher=\"IBM developerWorks\">  "
                + "      <article level=\"Advanced\" date=\"January-2004\">    "
                + "          <title>Design service-oriented architecture     "
                + "                 frameworks with J2EE technology</title>  "
                + "          <author>Naveen Balani</author>   "
                + "      </article> "
                + "      <article level=\"Advanced\" date=\"October-2003\">    "
                + "          <title>Advance DAO Programming</title>  "
                + "          <author>Sean Sullivan</author>   "
                + "      </article> " + "   </journal>  " + "</catalog> ";
    }

    /** Transform org.w3.dom.Document to XML. */
    @Test
    public void toXmlTest() throws Exception {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true); // never forget this!
        Document doc = domFactory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));

        //
        // javax.xml.transform (SUN) - ohne Zeilenumbrüche u.ä.
        //
        StringWriter sw = new StringWriter();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(doc), new StreamResult(sw));
        //domWriter.getDomConfig().setParameter("canonical-form", Boolean.FALSE);
        assertTrue(sw.toString().length() > 100);


        //
        // org.apache.xml (Xerces) - schön
        //
        sw = new StringWriter();
        OutputFormat format = new OutputFormat(doc, "UTF-8", true);
        new XMLSerializer(sw, format).serialize(doc);
        assertTrue(sw.toString().length() > 100);


        //
        // org.jdom - schön
        //
        sw = new StringWriter();
        org.jdom.Document jdoc = new org.jdom.input.DOMBuilder().build(doc);
        new XMLOutputter(Format.getPrettyFormat()).output(jdoc, sw);
        Assert.assertTrue(sw.toString().length() > 100);
    }

}
