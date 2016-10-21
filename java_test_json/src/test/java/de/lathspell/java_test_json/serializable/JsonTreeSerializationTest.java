package de.lathspell.java_test_json.serializable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class JsonTreeSerializationTest {

    @Test
    public void test() throws Exception {
        // Create data object
        JsonNode jnode = JsonNodeFactory.instance.numberNode(42);
        ObjectNode onode = JsonNodeFactory.instance.objectNode().put("foo", "bar");
        MyData data = new MyData(jnode, onode);
        
        // Serialize data object into byte stream
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(data);
        }
        byte[] buffer = bos.toByteArray();

        // Deserialize byte stream into data object
        MyData data2;
        ByteArrayInputStream bis = new ByteArrayInputStream(buffer);
        try (ObjectInputStream ois = new ObjectInputStream(bis)) {
            data2 = (MyData) ois.readObject();
        }
        
        // Compare results
        assertEquals(data.toString(), data2.toString());
    }
}
