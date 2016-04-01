package de.lathspell.test.javascript;

import javax.script.ScriptEngineManager;

import org.junit.Test;

public class ListTest {

    @Test
    public void testList() {
        new ScriptEngineManager().getEngineFactories().stream().forEach((f) -> {
            // "Oracle Nashorn (1.8.0_72-internal-b15, args) provides ECMAScript" [js]
            // jython (2.7.3) provides python [py]
            System.out.printf("%s (%s) provides %s %s\n", f.getEngineName(), f.getEngineVersion(), f.getLanguageName(), f.getExtensions());
        });
    }

}
