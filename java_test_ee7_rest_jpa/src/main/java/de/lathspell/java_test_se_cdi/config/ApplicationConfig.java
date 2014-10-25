package de.lathspell.java_test_se_cdi.config;

import java.io.InputStream;
import java.util.Properties;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationConfig {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);
    private static Properties properties;

    /**
     * Loads environment specific properties file from classpath.
     *
     * @param env The filename will be env + ".properties"
     * @throws Exception If the properties file is not found.
     */
    public static void setEnv(String env) throws Exception {
        String fname = env + ".properties";
        log.info("Using properties file " + fname);
        InputStream is = ClassLoader.getSystemResourceAsStream(fname);
        if (is == null) {
            throw new Exception("Environment specific properties file '" + fname + "' was not found in classpath!");
        }
        properties = new Properties();
        properties.load(is);
    }

    /**
     * Creates an SLF Logger with the declaring class as name.
     */
    @Produces
    public Logger produceLogger(InjectionPoint ip) throws Exception {
        return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
    }

    @Produces
    @PropertyValue
    public String producePropertyValue(InjectionPoint ip) throws Exception {
        // String fqn = ip.getMember().getDeclaringClass().getName() + "." + ip.getMember().getName();
        PropertyValue pv = ip.getAnnotated().getAnnotation(PropertyValue.class);
        return properties.getProperty(pv.value(), pv.defaultValue());
    }
}