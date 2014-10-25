package de.lathspell.java_test_xml.xsd2java;

import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import de.lathspell.java_test_xml.xsd2java.generated.Item;
import de.lathspell.java_test_xml.xsd2java.generated.Items;

// xjc -d ~/workspace/java_test/src/main/java -p de.lathspell.test.xml.xsd2java.generated Items.xsd

public class XML2Java {
    @Test
    public void TestImport() throws Exception {
        JAXBContext context = JAXBContext.newInstance("de.lathspell.java_test_xml.xml2java.generated");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Items items = (Items) unmarshaller.unmarshal(new FileReader("src/main/java/de/lathspell/java_test_xml/xml2java/Items.xml"));
        List<Item> listOfItems = items.getItem();

        assertEquals(listOfItems.get(1).getId(), "TV001");
    }
}
