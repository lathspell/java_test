package de.lathspell.java_test_json.csv;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

class PopulationDeserializer extends JsonDeserializer<Integer> {

    @Override
    public Integer deserialize(JsonParser jp, DeserializationContext ctx) throws IOException, JsonProcessingException {
        JsonNode node = jp.readValueAsTree();
        
        return Integer.valueOf(node.asText().replace(".", ""));
    }

}
