package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import static org.apache.camel.LoggingLevel.INFO;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.junit.Test;

import de.lathspell.test.java_test_camel.model.Person;
import de.lathspell.test.java_test_camel.model.PersonConverter;

@Slf4j
public class SimpleTest {

    private static boolean stopped = false;

    private String body;

    @Test
    public void test1() throws Exception {
        RouteBuilder builder = new RouteBuilder() {
            @Override
            public void configure() {
                /* URI "file:"
                 * - file: https://camel.apache.org/file2.html
                 * - "fileName" is for a single file
                 * - "include" uses regex
                 * - "noop" means no delete or rename (for tests)
                 *
                 * URI "log:"
                 * - log: https://camel.apache.org/log.html
                 *
                 * validate(): http://camel.apache.org/validate.html
                 * transform(): http://camel.apache.org/message-translator.html
                 * 
                 */
 /* does not work yet:
                from("timer://runOnce?repeatCount=1")
                        .routeId("runOnce")
                        .to("direct:findFile");
                 */
                from("file://src/main/resources/inputs/?include=input.*\\.txt&noop=true")
                        .routeId("findFile")
                        .log(INFO, "### Found file: |${in.body}|")
                        .to("log:Received?level=WARN&showAll=true&multiline=true") /* of questionable use */
                        .convertBodyTo(String.class)
                        .log(INFO, "### Body is now: |${in.body}|")
                        .transform(body().regexReplaceAll("\\n*$", ""))
                        .log(INFO, "### Body is now: |${in.body}|")
                        .validate(body().regex("^Hello .*$"))
                        .log(INFO, "### Body is now: |${in.body}|")
                        .toF("bean:body", "${body}")
                        .convertBodyTo(Person.class)
                        .log(INFO, "### Body is now: |${in.body}|")
                        .marshal().json(JsonLibrary.Jackson)
                        .log(INFO, "### Body is now: |${in.body}|")
                        .to("file://outputs/")
                        .log(INFO, "### Finished")
                        .bean(SimpleTest.class, "stop");
                // alternatively: .to("controlbus:route?routeId=findFile&action=stop&async=true");
            }
        };

        log.info("------- setup------");
        CamelContext myCamelContext = new DefaultCamelContext();
        /* useful for debugging the message flow; does not print contents */
        myCamelContext.setTracing(true);
        /* http://camel.apache.org/mdc-logging.html for "messageId" and "routeId" */
        myCamelContext.setUseMDCLogging(true);
        myCamelContext.getTypeConverterRegistry().addTypeConverters(new PersonConverter());
        myCamelContext.addRoutes(builder);
        myCamelContext.start();
        log.info("------- started ------");
        while (!stopped) {
            log.info("------- sleeping ------");
            Thread.sleep(1000 * 1);
        }
        log.info("------- stopping ------");
        myCamelContext.stop();
        log.info("------- end ------");
        log.info("------- body: " + body + " ------");
    }

    public static void stop() {
        log.info("-------- stop requested ---------");
        stopped = true;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
