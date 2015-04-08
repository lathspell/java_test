package my;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.*;

import static my.JsonPatchOp.TEST;

public class JsonPatchTest {

    @Test
    public void testJsonPatchLine() throws Exception {
        String json = "{ \"op\": \"test\", \"path\": \"/domain\", \"value\": \"example.com\" }";

        JsonPatchLine line = new ObjectMapper().readValue(json, JsonPatchLine.class);
        assertEquals("example.com", line.getValue());
    }

    @Test
    public void testJsonPatch() throws Exception {
        String json = "[ { \"op\": \"test\", \"path\": \"/domain\", \"value\": \"example.com\" } ]";

        JsonPatch patch = new ObjectMapper().readValue(json, JsonPatch.class);
        assertEquals("example.com", patch.getValue(TEST, "/domain"));
    }
}
