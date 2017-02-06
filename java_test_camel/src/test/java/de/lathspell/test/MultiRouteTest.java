package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

@Slf4j
public class MultiRouteTest {

    @Test
    public void test() throws Exception {
        RouteBuilder builder = new RouteBuilder() {
            @Override
            public void configure() {
                from("timer://runOnce?repeatCount=1")
                        .routeId("runOnce")
                        .description("my test route")
                        .pollEnrich("file://src/main/resources/inputs/?include=input.*\\.txt&noop=true")
                        .convertBodyTo(String.class)
                        .log("# body: ${body}")
                        .recipientList(exchangeProperty("foo"));
            }
        };

        CamelContext myCamelContext = new DefaultCamelContext();
        myCamelContext.setUseMDCLogging(true);
        myCamelContext.addRoutes(builder);
        myCamelContext.start();

        Thread.sleep(1000 * 5);
        log.info("Context Prop: " + myCamelContext.getProperty("foo"));
        String foo = (String) myCamelContext.getRoute("runOnce").getProperties().get("foo");
        assertEquals("Hello World", foo);
        // FIXME
    }
}
