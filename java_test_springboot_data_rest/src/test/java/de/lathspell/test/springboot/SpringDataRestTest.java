package de.lathspell.test.springboot;

import java.io.IOException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.Family.SUCCESSFUL;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

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

    private final String URL = "http://localhost:9090";

    private ObjectMapper om = new ObjectMapper().enable(INDENT_OUTPUT);

    private WebTarget target;

    @Before
    public void before() {
        target = ClientBuilder.newClient().target(URL);
    }

    @Test
    public void test1() throws IOException {
        JsonNode node = getPeopleNode();
        assertEquals(0, node.at("/page/totalElements").asInt());

        // Create
        String personJson = "{ \"firstName\": \"Max\", \"lastName\": \"Mustermann\" }";
        String ret = target.path("/people/").request(APPLICATION_JSON).post(Entity.json(personJson), String.class);
        node = om.readTree(ret);
        assertTrue(node.at("/_links/self/href").asText().endsWith("/people/1"));

        // ... check
        node = getPeopleNode();
        assertEquals(1, node.at("/page/totalElements").asInt());
        
        // Retrieve
        Person p1 = findNodeById(1);
        // assertEquals(1, p1.getId()); // scheint von Spring Data Rest unterdrückt zu werden
        assertEquals("Max", p1.getFirstName());
        assertEquals("Mustermann", p1.getLastName());
        
        // Search
        String results = target.path("/people/search/findByLastName").queryParam("name", "Mustermann").request(APPLICATION_JSON).get(String.class);
        JsonNode resultsNode = om.readTree(results);
        String resultJson = resultsNode.at("/_embedded/people/0").toString();
        p1 = om.readValue(resultJson, Person.class);
        assertEquals("Mustermann", p1.getLastName());
        
        // Update
        p1.setFirstName("Erika");
        Response r = target.path("/people/1").request(APPLICATION_JSON).put(Entity.json(p1));
        assertEquals(SUCCESSFUL, r.getStatusInfo().getFamily());
        
        // ... check
        Person p2 = findNodeById(1);
        assertEquals("Erika", p2.getFirstName());
        assertEquals("Mustermann", p2.getLastName());
        
        // Delete
        r = target.path("/people/1").request().delete();
        assertEquals(SUCCESSFUL, r.getStatusInfo().getFamily());
        
        // ... check
        r = target.path("/people/1").request(APPLICATION_JSON).get();
        assertEquals(NOT_FOUND.getStatusCode(), r.getStatus());
    }

    private Person findNodeById(int id) {
        return target.path("/people/" + id).request(APPLICATION_JSON).get(Person.class);
    }

    private JsonNode getPeopleNode() throws IOException {
        String json = target.path("/people/").request(APPLICATION_JSON).get(String.class);
        JsonNode node = om.readTree(json);
        assertFalse(node.isNull());
        return node;
    }

}
