package de.lathspell.java_test_ee6_jsf_selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.junit.Assert.*;

public class MyBeanTest {

    private static final String deploymentUrl = "http://localhost:8080/java_test_ee6_jsf_selenium/faces/";
    private static RemoteWebDriver browser;

    @BeforeClass
    public static void setUpClass() {
        browser = new FirefoxDriver();
    }
    
    @AfterClass
    public static void tearDownClass() {
        browser.close();
    }

    @Test
    public void testMyBeanForm() {
        browser.get(deploymentUrl + "index.xhtml");
        // System.out.println(browser.getPageSource());
        assertEquals("NoName", browser.findElementById("nameSpan").getText());

        browser.findElementById("myForm:nameInput").clear();
        browser.findElementById("myForm:nameInput").sendKeys("foo");
        browser.findElementByCssSelector("#myForm input[type='submit']").click();

        assertEquals("foo", browser.findElementById("nameSpan").getText());
    }
}
