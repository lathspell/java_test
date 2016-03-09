package de.lathspell.test.lombok.pojos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

public class DataObjectsTest {

    @Test
    public void atValueBuilder() {
        // ctor
        AtValueBuilderPojo obj = AtValueBuilderPojo.builder().name("Foobar").age(42).build();
        // getter
        assertEquals("Foobar", obj.getName());
        assertEquals(42, obj.getAge());
        // no setter since @value generates *immutable* data objects
    }

    @Test
    public void atFluentData() {
        // ctor and fluent setter
        AtFluentDataPojo obj = new AtFluentDataPojo("Foobar").age(42);
        // getter
        assertEquals("Foobar", obj.name());
        assertEquals(42, obj.age());
        // setter
        obj.age(21);
        assertEquals(21, obj.age());
    }

    @Test
    public void atValue() {
        // ctor
        AtValuePojo obj = new AtValuePojo("Foobar", 42);
        // getter
        assertEquals("Foobar", obj.getName());
        assertEquals(42, obj.getAge());
        // no setter since @value generates *immutable* data objects
    }

    @Test
    public void atData() {
        // ctor
        AtDataPojo obj = new AtDataPojo("Foobar");
        // setter
        obj.setAge(42);
        // getter
        assertEquals("Foobar", obj.getName());
        assertEquals(42, obj.getAge());
    }

    @Test
    public void atWither() {
        // ctor and fluent setter
        AtWitherPojo obj = new AtWitherPojo("Foobar", 42);
        // getter
        assertEquals("Foobar", obj.getName());
        assertEquals(42, obj.getAge());
        // no setter since @value generates immutable objects
        // wither return *new* objects!
        AtWitherPojo obj2 = obj.withAge(21);
        assertNotEquals(obj, obj2);
        assertEquals(42, obj.getAge());
        assertEquals("Foobar", obj2.getName());
        assertEquals(21, obj2.getAge());
    }
}
