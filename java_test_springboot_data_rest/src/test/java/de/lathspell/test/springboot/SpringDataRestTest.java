package de.lathspell.test.springboot;

import java.io.IOException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED;
import static javax.ws.rs.core.Response.Status.OK;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import de.lathspell.test.springboot.model.Person;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest({"server.port=9090"})
public class SpringDataRestTest {

    private final String URL = "http://localhost:9090/rest";

    private ObjectMapper om = new ObjectMapper().enable(INDENT_OUTPUT);

    private WebTarget target;

    @Before
    public void before() {
        target = ClientBuilder.newClient().target(URL);
    }

    @Test
    public void test1() throws IOException {
        // count before
        assertEquals(2, getPersonsNode().at("/page/totalElements").asInt());

        // Create
        String personJson = "{ \"firstName\": \"Manfred\", \"lastName\": \"Mustermann\" }";
        String ret = target.path("/persons/").request(APPLICATION_JSON).post(Entity.json(personJson), String.class);
        assertTrue(om.readTree(ret).at("/_links/self/href").asText().endsWith("/persons/3"));

        // count after
        assertEquals(3, getPersonsNode().at("/page/totalElements").asInt());

        // Retrieve
        Person p3 = target.path("/persons/3").request(APPLICATION_JSON).get(Person.class);
        assertEquals("Manfred", p3.getFirstName());
        assertEquals("Mustermann", p3.getLastName());
    }

    @Test
    public void search() throws Exception {
        String results = target.path("/persons/search/findByLastName").queryParam("name", "Mustermann").request(APPLICATION_JSON).get(String.class);
        JsonNode resultsNode = om.readTree(results);
        String lastFirstName = resultsNode.at("/_embedded/person/0/lastFirstName").asText();
        assertEquals("Mustermann, Max", lastFirstName);
    }

    @Test
    public void update() {
        Person p = target.path("/persons/2").request(APPLICATION_JSON).get(Person.class);
        assertEquals("Erika", p.getFirstName());

        p = new Person();
        p.setFirstName("Else");
        p.setLastName("Musterfrau");
        Response r = target.path("/persons/2").request(APPLICATION_JSON).put(Entity.json(p));
        assertEquals(OK.getStatusCode(), r.getStatus());
        Person p2 = r.readEntity(Person.class);
        assertEquals("Else", p2.getFirstName());

        // ... check
        p = target.path("/persons/2").request(APPLICATION_JSON).get(Person.class);
        assertEquals("Else", p.getFirstName());
        assertEquals("Musterfrau", p.getLastName());
    }

    @Test
    public void delete() {
        Response r = target.path("/persons/3").request().delete();
        assertEquals(METHOD_NOT_ALLOWED.getStatusCode(), r.getStatus());
    }

    private JsonNode getPersonsNode() throws IOException {
        String json = target.path("/persons/").request(APPLICATION_JSON).get(String.class);
        JsonNode node = om.readTree(json);
        assertFalse(node.isNull());
        return node;
    }

}
