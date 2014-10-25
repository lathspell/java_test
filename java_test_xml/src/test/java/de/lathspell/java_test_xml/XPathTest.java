package de.lathspell.java_test_xml;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XPathTest {
    String xml;

    String xmlDefaultNS;

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

        xmlDefaultNS = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<PcspService xmlns=\"http://www.cablelabs.com/Pcsp/I01/schema\" xmlns:cpc=\"http://www.cedarpointcom.com/Schema\">"
                + "    <ServiceId>22122250044</ServiceId>"
                + "    <AdminStatus>1</AdminStatus>"
                + "   <DisplayName>SIP-IAD-Test</DisplayName>"
                + "    <InterExchange />" + "    <LNP>"
                + "        <PortingStatus>0</PortingStatus>"
                + "        <LNPT>false</LNPT> </LNP> </PcspService>";
    }

    @Test
    public void xPathTest() throws Exception {
        String ret;
        InputSource xmlInputSource;

        XPath xPath = XPathFactory.newInstance().newXPath();

        // Ein einzelnes Attribut
        xmlInputSource = new InputSource(new StringReader(xml));
        ret = xPath.evaluate(
                "/catalog/journal/article[@date='January-2004']/@level",
                xmlInputSource);
        Assert.assertEquals(ret, "Advanced");

        // Ein einzelnes Element
        xmlInputSource = new InputSource(new StringReader(xml));
        ret = xPath.evaluate(
                "//catalog/journal/article[@date='October-2003']/author",
                xmlInputSource);
        Assert.assertEquals(ret, "Sean Sullivan");
    }

    @Test
    public void xPathMitDocumentTest() throws Exception {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory
                .newInstance();
        domFactory.setNamespaceAware(true); // never forget this!
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xml)));

        XPath xPath = XPathFactory.newInstance().newXPath();

        NodeList nodes;

        nodes = (NodeList) xPath.evaluate("//catalog/journal/article/@date",
                doc, XPathConstants.NODESET);
        Assert.assertEquals(nodes.item(0).getNodeValue(), "January-2004");
        Assert.assertEquals(nodes.item(1).getNodeValue(), "October-2003");

        nodes = (NodeList) xPath.evaluate(
                "//catalog/journal/article/author/text()", doc,
                XPathConstants.NODESET);
        Assert.assertEquals(nodes.item(0).getNodeValue(), "Naveen Balani");
        Assert.assertEquals(nodes.item(1).getNodeValue(), "Sean Sullivan");
    }

    @Test
    /**
     * Probleme bei der Verwendung eines Default-Namespace.
     * 
     * Das ist ziemlich häßlich weil normales xPath hier einfach nur null
     * liefert! Probleme bei der Verwendung von xPath und Default-Namespace sind
     * hier beschrieben: http://www.edankert.com/defaultnamespaces.html
     */
    public void defaultNamespaceTest() throws Exception {
        String ret;
        InputSource xmlInputSource;

        //
        // xPath mit speziellem Namespace-Mapper
        //
        // TODO

        //
        // xPath mit local-name()
        //
        XPath xPath = XPathFactory.newInstance().newXPath();
        xmlInputSource = new InputSource(new StringReader(xmlDefaultNS));
        ret = xPath.evaluate(
                        "//*[local-name()='DisplayName']/text()",
                        xmlInputSource);
        Assert.assertEquals(ret, "SIP-IAD-Test");

        //
        // DOM
        //
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true); // never forget this!
        Document doc = domFactory.newDocumentBuilder().parse(new InputSource(new StringReader(xmlDefaultNS)));

        // navigate
        ret = doc.getElementsByTagName("DisplayName").item(0).getTextContent();
        Assert.assertEquals(ret, "SIP-IAD-Test");
    }
}
