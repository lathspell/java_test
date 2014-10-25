package de.lathspell.java_test_json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

// Why? @JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "x1", // does not work for additionalProperties!
    "_links",
    "published_at",
    "title",
    "body",
    "isVisible",
    "votes",
    "tags"
})
public class Article {

    @JsonProperty("published_at")
    private Date publishedAt;

    private String title;

    private String body;

    private boolean isVisible;

    private double votes;

    private List<String> tags;

    @JsonDeserialize(as=ObjectNode.class)
    private ObjectNode _links;
    
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new TreeMap<>();

    /**
     * A no-argument constructor is necessary for JSON unmarshalling.
     */
    public Article() {

    }

    public Article(String title, String body) {
        this(title, body, new Date(), true, 0.0, new ArrayList<String>());
    }

    public Article(String title, String body, Date publishedAt, boolean isVisible, double votes, List<String> tags) {
        this.publishedAt = publishedAt;
        this.title = title;
        this.body = body;
        this.isVisible = isVisible;
        this.votes = votes;
        this.tags = tags;
    }

    public String toCSV() {
        return "" + publishedAt + ";" + title + ";" + body + ";" + isVisible + ";" + votes;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String key, Object value) {
        System.out.println("Storing additional property " + key + ": " + value);
        this.additionalProperties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        System.out.println("Returning additional properties.");
        return this.additionalProperties;
    }
    
    @JsonProperty("_links")
    public void setLinksFromJson(ObjectNode obj) throws IOException {
        System.out.println("Setting _links with: " + obj.getClass() + ": " + obj);
        _links = obj;
        //String json = new ObjectMapper().writeValueAsString(obj);
        //_links = (ObjectNode) new ObjectMapper().readTree(json);
    }
    
    @JsonProperty("_links")
    public ObjectNode getLinks() {
        return _links;
    }

    @JsonProperty("published_at")
    public Date getPublishedAt() {
        return publishedAt;
    }

    @JsonProperty("published_at")
    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public double getVotes() {
        return votes;
    }

    public void setVotes(double votes) {
        this.votes = votes;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
