package de.lathspell.java_test_xml;

import java.io.File;
import java.io.StringWriter;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import static org.junit.Assert.*;

import de.lathspell.java_test_xml.model.Address;
import de.lathspell.java_test_xml.model.Book;
import de.lathspell.java_test_xml.model.Company;
import de.lathspell.java_test_xml.model.Job;
import de.lathspell.java_test_xml.helper.JobHelper;
import de.lathspell.java_test_xml.model.Parts;

public class DTDTest {

    @Test
    public void test1() throws Exception {
        Job job = new Job();
        job.setAction("create");
        job.setObjectId("K24S4");
        job.setDate("2013-08-04");
        job.setCustomerId("12345678");
        job.setRequestor("me@example.com");
        Parts parts = new Parts();
        job.setParts(parts);

        Address address1 = new Address();
        address1.setCity("Köln");
        address1.setStreet("Neumarkt 1");
        Company company = new Company();
        company.setvalue("Example GmbH");
        address1.getNameOrCompany().add(company);
        parts.setAddress(address1);

        Book book1 = new Book();
        book1.setAuthor("Max Mustermann");
        book1.setTitle("README");
        book1.setDate("1900-01-01");
        parts.setBook(book1);

        checkXml("test1", JobHelper.toXml(job));
    }

    @Test(expected = SAXException.class)
    public void testInvalidJobAction() throws Exception {
        Job job = new Job();
        job.setAction("create");

        JobHelper.toXml(job);
    }

    /**
     * Checks expected and actual XML.
     *
     * Saves a copy of the actual for easy "/usr/bin/diff -u" below "target/test-classes/".
     * Any "DOCTYPE" line in the expected XML will be removed prior to comparison.
     *
     * @param basename      Filename without .xml or .out.xml.
     * @param actual
     * @throws Exception
     */
    private void checkXml(String basename, String actual) throws Exception {
        String expected = IOUtils.toString(getClass().getResourceAsStream(basename + ".xml"), Charsets.ISO_8859_1);
        expected = expected.replaceFirst("<!DOCTYPE .+?>\n", ""); // don't expect this

        FileUtils.write(new File("target/test-classes/de/lathspell/java_test_xml/" + basename + ".out.xml"), actual, Charsets.ISO_8859_1);
        assertEquals(expected, actual);
    }
}
