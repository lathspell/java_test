package de.lathspell.test;

import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class JavaScriptTest {

    /**
     * Javascript from Java.
     *
     * An JSR-233 compatible implementation has to be in the CLASSPATH.
     * Here org.mozilla.rhino is used.
     *
     * FIXME: Currently it is not found. Dunno why.
     *
     */
    @Test
    public void testJS() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();

        //
        // List available
        //
        List<ScriptEngineFactory> factories = manager.getEngineFactories();
        assertTrue("No JavaScript engines found!", factories.size() > 1);
        for (ScriptEngineFactory factory : factories) {
            System.out.printf("%s (%s, args) provides %s\n", factory.getEngineName(), factory.getEngineVersion(),
                    factory.getLanguageName());
        }

        //
        // Run script
        //
        ScriptEngine engine = manager.getEngineByName("javascript");
        engine.eval("print('Hello ')");

        //
        // Run precompiled function
        //
        engine.eval("function printIt(s) { print(s); }");
        Invocable inv = (Invocable) engine;
        inv.invokeFunction("printIt", "World");
    }
}
