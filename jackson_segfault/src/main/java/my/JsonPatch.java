package my;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements a JSON patch.
 *
 * Aktuell nur einen read-only Patch, weil das reichte.
 *
 * <pre>
 * [
 *   { "op": "test",    "path": "/domain",        "value": "example.com" },
 *   { "op": "test",    "path": "/customerNo",    "value": "10199999" },
 *   { "op": "test",    "path": "/customerEmail", "value": "alt@example.com" },
 *   { "op": "replace", "path": "/transactionId", "value": "1234" },
 *   { "op": "replace", "path": "/customerNo",    "value": "22299999" },
 *   { "op": "replace", "path": "/customerEmail", "value": "neu@example.com" }
 * ]
 * </pre>
 */
public class JsonPatch {

    private LinkedList<JsonPatchLine> lines;

    /*
     @JsonCreator
     public JsonPatch(String json) throws IOException {
     System.err.println("************* list=" + json);
     lines = new LinkedList<>();
     }*/
    /*
    @JsonCreator
    public JsonPatch(List<String> list) throws IOException {
        System.err.println("************* list=" + list);
    }*/

    @JsonCreator
    public JsonPatch(List<JsonPatchLine> list) throws IOException {
        System.err.println("************* list=" + list);
    }
    
    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(lines);
        } catch (JsonProcessingException e) {
            return super.toString();
        }
    }

    public JsonPatchLine getLine(int i) {
        return lines.get(i);
    }

    public LinkedList<JsonPatchLine> getLines() {
        return lines;
    }

    public LinkedList<JsonPatchLine> getLines(JsonPatchOp op) {
        // Wir warten auf Java 8...
        LinkedList<JsonPatchLine> filtered = new LinkedList<>();
        for (JsonPatchLine line : lines) {
            if (line.getOp() == op) {
                filtered.add(line);
            }
        }
        return filtered;
    }

    /**
     * Liefert den Value-Teil der Zeile oder eine Exception.
     *
     * @param op   "TEST"
     * @param path "/customerNo"
     * @return Ein Wert und niemals NULL.
     * @throws Exception Falls kein Wert gefunden oder Wert gleich Null ist.
     */
    public String getValue(JsonPatchOp op, String path) throws Exception {
        for (JsonPatchLine line : lines) {
            if (line.getOp() == op && line.getPath().equals(path)) {
                if (line.getValue() == null) {
                    throw new Exception("Zeile mit " + op + " '" + path + "' hat keinen Value-Wert!");
                }
                return line.getValue();
            }
        }
        throw new Exception("Zeile mit " + op + " '" + path + "' nicht gefunden!");
    }

    /**
     * Liefert den Value-Teil der Zeile oder den angegebenen Default-Wert.
     *
     * @param op           "TEST"
     * @param path         "/customerNo"
     * @param defaultValue z.B. NULL
     * @return Wert aus Patch oder Default-Wert aber niemals Exception.
     */
    public String getValue(JsonPatchOp op, String path, String defaultValue) {
        for (JsonPatchLine line : lines) {
            if (line.getOp() == op && line.getPath().equals(path)) {
                return line.getValue();
            }
        }
        return defaultValue;
    }

    /**
     * Liefert den Value-Teil der Zeile oder den angegebenen Default-Wert.
     *
     * @param <T>         Beliebiger Typ
     * @param op          "TEST"
     * @param path        "/customerNo"
     * @param jsonDefault Der Wert wird als JSON in den gewünschten Typ konvertiert
     * @param ref         Gewünschter Typ
     * @return Wert aus Patch oder Default-Wert
     * @throws IOException Von ObjectMapper
     */
    public <T> T getValue(JsonPatchOp op, String path, String jsonDefault, TypeReference<T> ref) throws IOException {
        for (JsonPatchLine line : lines) {
            if (line.getOp() == op && line.getPath().equals(path)) {
                return new ObjectMapper().readValue(line.getValue(), ref);
            }
        }
        return new ObjectMapper().readValue(jsonDefault, ref);
    }
}
