package de.lathspell.java_test_se_cdi;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lathspell.java_test_se_cdi.config.ApplicationConfig;

public class Cli {

    private static Logger log = LoggerFactory.getLogger(Cli.class);

    public static void main(String[] args) throws Exception {
        // Select environment
        String env = System.getProperty("env");
        if (env == null) {
            throw new Exception("Need env property!");
        }
        log.info("Using environment: " + env);

        // Selecting CDI environment
        ApplicationConfig.setEnv(env);

        // Initialize CDI
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();

        GreeterBean greeterBean = container.instance().select(GreeterBean.class).get();
        System.out.println(greeterBean.getGreeting());

        weld.shutdown();

    }
}
