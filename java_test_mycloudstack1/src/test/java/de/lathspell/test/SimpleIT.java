package de.lathspell.test;

import org.junit.Test;

/**
 * Simple Integration-Test that depends on the Docker container started by Maven.
 */
// @SpringBootTest(webEnvironment = DEFINED_PORT) -- eben nicht, wir haben hier einen Blackbox Test!
public class SimpleIT {

    
    @Test
    public void testDockerContainer() {
        
    }
}
