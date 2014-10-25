package de.lathspell.java_test_ee6.rest;

import java.util.Map;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

/**
 * Overview at http://localhost:8080/java_test_ee6/rest/application.wadl
 *
 * The "/rest" is configured in org.netbeans.rest.application.config.ApplicationConfig.java
 */
@Path("/test")
public class MyRestResource {

    /**
     * View at http://localhost:8080/java_test_ee6/rest/test/hello/christian
     *
     * @param name Displayed name.
     * @return Greeting string.
     */
    @GET
    @Path("/hello/{name: [a-zA-Z]+}")
    @Produces("text/html")
    public String hello(@DefaultValue("nobody") @PathParam("name") String name) {
        return "Hello <em>" + name + "</em>!";
    }

    @XmlRootElement(name = "product")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class FullNameTO {

        @XmlElement(required = true)
        String firstName;
        @XmlElement(required = true)
        String lastName;
    }

    @GET
    @Path("/getFullName")
    @Produces({"application/xml", "application/json"})
    public FullNameTO getFullName() {
        FullNameTO fn = new FullNameTO();
        fn.firstName = "John";
        fn.lastName = "Doe";

        return fn;
    }

    @POST
    @Path("/checkName")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean checkName(@NotNull @FormParam("name") String Name) {
        return false;
    }

    @GET
    @Path("/requestInfo")
    @Produces(MediaType.APPLICATION_XML)
    public String requestInfo(@Context HttpHeaders hh, @Context UriInfo ui) {
        MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        MultivaluedMap<String, String> pathParams = ui.getPathParameters();
        MultivaluedMap<String, String> headerParams = hh.getRequestHeaders();
        Map<String, Cookie> cookieParams = hh.getCookies();
        return StringUtils.join(headerParams.keySet(), ",");
    }
}
