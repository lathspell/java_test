package my;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum JsonPatchOp {

    TEST,
    REPLACE;

    @JsonCreator
    public static JsonPatchOp forValue(String value) {
        return valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name().toLowerCase();
    }
}
