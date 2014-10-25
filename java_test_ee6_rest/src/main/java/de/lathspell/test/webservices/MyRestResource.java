package de.lathspell.test.webservices;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import static javax.ws.rs.core.MediaType.*;
import static javax.ws.rs.core.Response.Status.*;

import org.apache.commons.lang.StringUtils;

import de.lathspell.test.webservices.exceptions.ApiProblemException;

/**
 * Example REST resource.
 *
 * Overview at http://localhost:8080/java_test_ee6_rest/webresources/application.wadl
 * Pages at http://localhost:8080/java_test_ee6_rest/webresources/myrest/hello/christian
 *
 * Several resources classes belong to one "application" which is defined in
 * ApplicationConfig.java. There is only one single application.wadl for all
 * classes.
 *
 * ApplicationConfig.java also defines the ApplicationPath("webresources")
 * annotation. So web browsers connecting to Glassfish/Tomcat have to use
 * e.g. /java_test_ee6_rest/webresources/ before everything that is defined
 * here. The junit test do not need that prefix.
 */
@Path("/myrest")
public class MyRestResource {
    //private static final Logger log = LoggerFactory.getLogger(MyRestResource.class);

    /**
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

    @XmlRootElement(name = "FullName")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class FullName {

        @XmlElement(required = true)
        public String firstName = "John";

        @XmlElement(required = true)
        public String lastName = "Doe";

        int age = 42; // XML but not JSON!

        public String other;

    }

    @GET
    @Path("/getFullName")
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    public FullName getFullName() {
        return new FullName();
    }

    /**
     * Did not yet understand what this is about.
     * Seems to return the same as getFullName().
     */
    /* FIXME:
     @GET
     @Path("/getFullNameWithPadding")
     @Produces(APPLICATION_JSON)
     public JSONWithPadding getFullNameWithPadding() {
     return new JSONWithPadding(new FullName(), "myCallBack");
     }*/
    /**
     * WARNING: Madness ahead!
     *
     * A JSON text, according to RFC 4627 starts with either an object or
     * an array but *not* with a scalar. Inside the JSON text, single values
     * might be primitives like numbers, though.
     *
     * Although the Jackson library will return "42" as the result here,
     * the client might reject it as invalid JSON text.
     *
     * To circumvent this problem, a wrapper class has to be used.
     * Or client and server have to agree on some other notation like e.g.
     * "\/Integer(42)\/" (with quotes) which is similar to how .NET transmits
     * Date() values. The problem apparently is with all immutable objects that
     * can only be constructed via their constructor and not by using setters.
     *
     * Further enthralling readings:
     * - http://stackoverflow.com/questions/16408950/jax-rs-jersey-getinteger-class-and-single-json-primitive-integer-values
     * - https://github.com/FasterXML/jackson-databind/issues/34
     * - http://www.ietf.org/rfc/rfc4627.txt
     * - http://jsonlint.com/
     *
     * But the simpler the standard, the more the exceptions!
     *
     * There comes SerializationConfig.Feature.WRAP_ROOT_VALUE to the rescue!
     * Using this feature in the ObjectMapperContextResolver class it is possible
     * to generate a JSON compatible text that might or might not hint the
     * how to de-serialize it to the proper class. It looks like:
     * {"Integer":42}
     * But alas the deserializer still throws an exception if I write
     * resource().path("/myrest/getInteger").get(Integer.class)!
     *
     * @return bogus crap
     */
    @GET
    @Path("/getInteger")
    @Produces(APPLICATION_JSON)
    public Integer getInteger() {
        return Integer.valueOf(42);
    }

    @GET
    @Path("/getInt")
    @Produces(APPLICATION_JSON)
    public int getInt() {
        return 42;
    }

    @GET
    @Path("/getDouble")
    @Produces(APPLICATION_JSON)
    public Double getDouble() {
        return 3.14;
    }

    @GET
    @Path("/getBoolean")
    @Produces(APPLICATION_JSON)
    public Boolean getBoolean() {
        return true;
    }

    @POST
    @Path("/postBoolean")
    @Produces(APPLICATION_JSON)
    public Boolean postBoolean() {
        return true;
    }

    @GET
    @Path("/getArray")
    @Produces(APPLICATION_JSON)
    public String[] getArray() {
        return new String[]{"a", "b", "c"};
    }

    @GET
    @Path("/getList")
    @Produces(APPLICATION_JSON)
    public List<String> getList() {
        return Arrays.asList("a", "b", "c");
    }

    @GET
    @Path("/getDate")
    @Produces(APPLICATION_JSON)
    public Date getDate() {
        return new GregorianCalendar(2013, 5 - 1, 8, 0, 31, 42).getTime();
    }

    @POST
    @Path("/checkName")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON)
    public Boolean checkName(@FormParam("name") String name) {
        return name.matches("^[a-zA-Z]+$");
    }

    @GET
    @Path("/requestInfo")
    @Produces(APPLICATION_XML)
    public String requestInfo(@Context HttpHeaders hh, @Context UriInfo ui) {
        MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        MultivaluedMap<String, String> pathParams = ui.getPathParameters();
        MultivaluedMap<String, String> headerParams = hh.getRequestHeaders();
        Map<String, Cookie> cookieParams = hh.getCookies();
        return StringUtils.join(headerParams.keySet(), ",");
    }

    /**
     * Test how Exceptions are represented.
     *
     * Two different ones so that they might appear separately in the WADL.
     *
     * @throws IllegalArgumentException
     * @throws IllegalAccessError
     */
    @GET
    @Path("/getException")
    public String getException() throws IllegalArgumentException, IllegalAccessError {
        throw new IllegalArgumentException("This is invalid!");
    }

    /**
     * This should be catched by MyRestExceptionMapper.
     *
     * @return never anything
     * @throws IllegalStateException
     */
    @GET
    @Path("/getExceptionMapped")
    public String getExceptionMapped() throws IllegalStateException {
        throw new IllegalStateException("Wrong state!");
    }

    /**
     * Test Exceptions according to the Nottingham RFC draft.
     *
     * Two different ones so that they might appear separately in the WADL.
     *
     * @throws IllegalArgumentException
     * @throws IllegalAccessError
     */
    @GET
    @Path("/throwApiProblemJson")
    @Produces(APPLICATION_JSON)
    public Response throwApiProblemJson() throws Exception {
        if (1 == 2) {
            return Response.ok("All fine!").build();
        } else {
            throw new ApiProblemException(BAD_REQUEST, "This is invalid!");
        }
    }

    /**
     * Rethrowing an Exception according to the Nottingham RFC draft.
     *
     * @return Is of type String, yet the actual result is an JSON-encoded error.
     * @throws Exception
     */
    @GET
    @Path("/throwApiProblemJson2")
    @Produces(APPLICATION_JSON)
    public String throwApiProblemJson2() throws Exception {
        try {
            throw new IllegalArgumentException("This is wrong!");
        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            map.put("key1", "value1");
            map.put("key2", "value2");
            throw new ApiProblemException(INTERNAL_SERVER_ERROR, e, "The transaction was already open.", "Transaction #1234", map);
        }
    }

}
