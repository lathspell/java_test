package de.lathspell.java_test_json.serializable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Data class for use in JsonTreeSerializationTest.
 * 
 * JsonNode, ObjectNode etc. do not implement Serializable as explained in
 * https://github.com/FasterXML/jackson-databind/issues/18 and
 * https://groups.google.com/forum/#!msg/jackson-user/yw0ZALkikkw/kRF6_8gcAgAJ
 */
public class MyData implements Serializable {

    private transient JsonNode jnode;
    private transient ObjectNode onode;

    public MyData(JsonNode jnode, ObjectNode onode) {
        this.jnode = jnode;
        this.onode = onode;
    }

    @Override
    public String toString() {
        return "MyData{" + "jnode=" + jnode + ", onode=" + onode + '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(new ObjectMapper().writeValueAsString(jnode));
        out.writeUTF(new ObjectMapper().writeValueAsString(onode));
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        String jnodeContent = in.readUTF();
        jnode = new ObjectMapper().readTree(jnodeContent);

        String onodeContent = in.readUTF();
        onode = (ObjectNode) new ObjectMapper().readTree(onodeContent);
    }

}
