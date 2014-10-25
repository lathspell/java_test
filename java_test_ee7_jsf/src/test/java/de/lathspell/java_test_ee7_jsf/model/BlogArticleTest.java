package de.lathspell.java_test_ee7_jsf.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.lathspell.java_test_ee7_jsf.blog.model.BlogArticle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 *
 * @author james
 */
public class BlogArticleTest {

    public BlogArticleTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getTitle method, of class BlogArticle.
     */
    @Test
    public void testGetTitle() {
        System.out.println("getTitle");
        BlogArticle instance = new BlogArticle();
        String expResult = "";
        String result = instance.getTitle();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTitle method, of class BlogArticle.
     */
    @Test
    public void testSetTitle() {
        System.out.println("setTitle");
        String title = "";
        BlogArticle instance = new BlogArticle();
        instance.setTitle(title);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getText method, of class BlogArticle.
     */
    @Test
    public void testGetText() {
        System.out.println("getText");
        BlogArticle instance = new BlogArticle();
        String expResult = "";
        String result = instance.getText();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setText method, of class BlogArticle.
     */
    @Test
    public void testSetText() {
        System.out.println("setText");
        String text = "";
        BlogArticle instance = new BlogArticle();
        instance.setText(text);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
