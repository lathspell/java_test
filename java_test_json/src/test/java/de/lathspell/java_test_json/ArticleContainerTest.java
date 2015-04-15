package de.lathspell.java_test_json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArticleContainerTest {

    @Test
    public void testJsonCreator() throws Exception {
        String json = "[ { \"title\": \"Foo\" }, { \"title\": \"Bar\" }]";

        ArticleContainer container = new ObjectMapper().readValue(json, ArticleContainer.class);

        assertEquals(2, container.getArticles().size());
        assertEquals("Bar", container.getArticles().get(1).getTitle());
    }
}
