package de.lathspell.java_test_ee6.rest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.util.ApplicationDescriptor;

public class MyRestResourceTest extends JerseyTest {

    public MyRestResourceTest() throws Exception {
        super();
        Map<String, String> INIT_PARAMS = new HashMap<String, String>();
        INIT_PARAMS.put("com.sun.jersey.config.feature.Redirect", "true");
        INIT_PARAMS.put("com.sun.jersey.config.feature.ImplicitViewables", "true");
        INIT_PARAMS.put("com.sun.jersey.config.property.WebPageContentRegex", "/(images|css|jsp)/.*");
        ApplicationDescriptor applicationDescriptor = new ApplicationDescriptor();
        applicationDescriptor = applicationDescriptor.setContextPath("test")
                .setRootResourcePackageName(MyRestResource.class.getPackage().toString())
                .setServletInitParams(INIT_PARAMS);
        super.setupTestEnvironment(applicationDescriptor);
    }

    @Test
    public void testExtendedWadl() throws Exception {
        String wadl = webResource.path("application.wadl").accept(MediaTypes.WADL).get(String.class);
        assertTrue("Generated wadl is of null length", wadl.length() > 0);
        assertTrue("Generated wadl doesn't contain the expected text", wadl.contains("This is a paragraph"));
    }

    @Test
    public void testHello() {
        assertEquals("Hello Christian", webResource.path("/hello/Christian").get(String.class));
        assertEquals("Hello ", webResource.path("/hello/").get(String.class));
    }
}