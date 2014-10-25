package de.lathspell.test.cdi.spring;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Required;
import de.lathspell.test.cdi.spring.greeter.AbstractGreeter;

/**
 * Example of Dependency Injection using Spring.
 *
 * - This class is a POJO with getter/setter methods.
 * - The DI configuration is mainly done by the AppConfig* classes that have
 * a
 *
 * @Configuration annotation on them. Their
 * @Bean annotated methods
 * work like CDI
 * @Produces.
 * - Additional configuration can be loaded at startup time from Spring XML
 * configuration or property style files.
 * - The standard Java CDI
 * @Inject annotation is also supported by spring
 * as demonstrated in the SpringExampleEnglishTest test class.
 * - The main advantage of using Spring is the possibility of using Profiles
 * ("Environments") that can be switched at runtime.
 *
 * To activate a profile use:
 *
 * <pre>-Dspring.profiles.active="profile1,profile2"</pre>
 *
 * or in web.xml:
 *
 * <pre>
 * {@code
 * <servlet>
 * <servlet-name>dispatcher</servlet-name>
 * <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
 * <init-param>
 * <param-name>spring.profiles.active</param-name>
 * <param-value>production</param-value>
 * </init-param>
 * </servlet>
 * }
 * <
 * /
 * pre>
 */
public class SpringExample {

    AbstractGreeter greeter;
    String punctuation;
    String smiley;
    private static Logger logger = LogManager.getLogger(SpringExample.class);

    public String getGreeting() {
        logger.entry();
        return "Localized greeter says: " + greeter.getGreeting() + punctuation + " " + smiley;
    }

    public AbstractGreeter getGreeter() {
        return greeter;
    }

    @Required
    public void setGreeter(AbstractGreeter greeter) {
        this.greeter = greeter;
    }

    public String getPunctuation() {
        return punctuation;
    }

    @Required
    public void setPunctuation(String punctuation) {
        this.punctuation = punctuation;
    }

    public String getSmiley() {
        return smiley;
    }

    @Required
    public void setSmiley(String smiley) {
        this.smiley = smiley;
    }
}
