package de.lathspell.test.javascript;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Executing Javascript from Java.
 *
 * A JSR-223 compatible implementation has to be in the CLASSPATH but there
 * is always at least one Javascript engine in the Java runtime.
 */
public class JavascriptTest {

    private static final ScriptEngine js = new ScriptEngineManager().getEngineByName("javascript");

    @BeforeClass
    public static void beforeClass() {
        assertNotNull("No JSR 233 Script Engine for Javascript found!", js);
    }

    @Test
    public void testExecution() throws Exception {
        assertEquals("foobar", js.eval("'foo' + 'bar'"));
    }

    @Test
    public void testBindings() throws Exception {
        js.put("x", 0);
        js.put("a", 2);
        js.put("b", 3);
        js.eval("x = Math.pow(a, b)");
        assertEquals(8, (int) js.get("x"), 0);
    }

    @Test
    public void testPrecompiledFunction() throws Exception {
        js.eval("function my_pow(x, y) { return Math.pow(x, y); }");

        Invocable invocable = (Invocable) js;
        int actual = (int) invocable.invokeFunction("my_pow", 2, 3);
        assertEquals(8, actual, 0);
    }

}
