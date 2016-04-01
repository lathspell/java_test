package de.lathspell.test.javascript;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PythonTest {

    private static final ScriptEngine python = new ScriptEngineManager().getEngineByName("python");

    @BeforeClass
    public static void beforeClass() throws ClassNotFoundException {
        assertNotNull("No JSR 233 Script Engine for Python found!", python);
    }

    @Test
    public void testExecution() throws Exception {
        assertEquals(5, python.eval("2+3"));
    }

    @Test
    public void testBinding() throws Exception {
        python.put("x", 0);
        python.put("a", 2);
        python.put("b", 3);
        python.eval("x = a * b");
        assertEquals(6, python.get("x"));
    }

}
