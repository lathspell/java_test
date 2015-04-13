package de.lathspell.java_test_json;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Testing conversion to and from JSON objects using the Jackson library.
 */
public class JacksonTest {

    /**
     * What happens if my model is only partially complete.
     *
     * The java.util.Date is converted to long according to Date.getTime().
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testPartialJavaBeanMarshalling() throws Exception {
        Article article = new Article("News!", "Some news...", new GregorianCalendar(1970, 0, 1).getTime(), true, 3.14, Arrays.asList("cool", "funny"));
        // Has two more attributes, x1 (int) and x2 (object). I've put them at the end as they will be serialized there automatically.
        String extendedArticleJson = "{\"_links\":{\"x:a\":{\"href\":\"foo\"}},\"published_at\":-3600000,\"title\":\"News!\",\"body\":\"Some news...\",\"isVisible\":true,\"votes\":3.14,\"tags\":[\"cool\",\"funny\"],\"x1\":42,\"x2\":{\"x2a\":2},\"x3\":[1,2,3]}";

        // big JSON to small POJO
        Article producedArticle = new ObjectMapper().readValue(extendedArticleJson, Article.class);
        assertEquals(article.toCSV(), producedArticle.toCSV());

        // The complete data structure below _links is stored as JsonNode to permit easy access.
        assertEquals("foo", producedArticle.getLinks().at("/x:a/href").textValue());

        // small POJO with additional parameters back to big JSON
        String producedJson = new ObjectMapper().writeValueAsString(producedArticle);
        assertEquals(extendedArticleJson, producedJson);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUntyped() throws Exception {
        @SuppressWarnings("rawtypes")
        Map articleMap = new LinkedHashMap();
        articleMap.put("body", "Some news...");
        articleMap.put("isVisible", true);
        articleMap.put("published_at", new GregorianCalendar(1970, 0, 1).getTime().getTime());
        articleMap.put("tags", Arrays.asList("cool", "funny"));
        articleMap.put("title", "News!");
        articleMap.put("votes", 3.14);
        String articleJson = "{\"body\":\"Some news...\",\"isVisible\":true,\"published_at\":-3600000,\"tags\":[\"cool\",\"funny\"],\"title\":\"News!\",\"votes\":3.14}";

        // Map to JSON
        String producedJson = new ObjectMapper().writeValueAsString(articleMap);
        Assert.assertEquals(articleJson, producedJson);

        // JSON to Map
        @SuppressWarnings("rawtypes")
        Map producedMap = new ObjectMapper().readValue(producedJson, Map.class); // Article.class needs non-argument ctor!
        for (Object keyObj : articleMap.keySet()) {
            String key = (String) keyObj;
            if (key.equals("published_at")) {
                assertEquals(String.valueOf(articleMap.get(key)), String.valueOf(producedMap.get(key))); // long vs. int
            } else {
                assertEquals(articleMap.get(key), producedMap.get(key));
            }
        }
    }

    /**
     * Warning, this is a valid JSON string but not a valid JSON text!
     *
     * The response of e.g. a REST method must be a valid JSON text which
     * must have outer {} or [].
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testPrimitives() throws Exception {
        assertEquals("\"foo\"", new ObjectMapper().writeValueAsString("foo"));
    }

    @Test
    public void testPrettyPrint() throws Exception {
        Article article = new Article("News!", "Some news...", new GregorianCalendar(1970, 0, 1).getTime(), true, 3.14, Arrays.asList("cool", "funny"));
        String producedJson = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(article);
        String prettyJson = "{\n"
                + "  \"_links\" : null,\n"
                + "  \"published_at\" : -3600000,\n"
                + "  \"title\" : \"News!\",\n"
                + "  \"body\" : \"Some news...\",\n"
                + "  \"isVisible\" : true,\n"
                + "  \"votes\" : 3.14,\n"
                + "  \"tags\" : [ \"cool\", \"funny\" ]\n"
                + "}";
        assertEquals(prettyJson, producedJson);

        Map<String, Object> map = new ObjectMapper().readValue(prettyJson, new TypeReference<Map<String, Object>>() {
        });
        assertNotNull(map);
    }
}
