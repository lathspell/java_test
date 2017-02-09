package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

@Slf4j
public class SimpleTimerTest {

    @Test
    public void test() throws Exception {
        RouteBuilder builder = new RouteBuilder() {
            @Override
            public void configure() {
                from("timer://runOnce?repeatCount=2")
                        .routeId("runOnceProducer")
                        .description("my test route")
                        .pollEnrich("file://src/main/resources/inputs/?include=input.*\\.txt&noop=true&idempotent=false&initialDelay=0")
                        .convertBodyTo(String.class)
                        .log("# body: ${body}")
                        .to("direct:runOnceResult");
            }
        };

        CamelContext camel = new DefaultCamelContext();
        camel.setUseMDCLogging(true);
        camel.addRoutes(builder);
        camel.start();

        ConsumerTemplate c = camel.createConsumerTemplate();
        String result = c.receiveBody("direct:runOnceResult", String.class);
        assertEquals("Hello World\n", result);
        String result2 = c.receiveBody("direct:runOnceResult", String.class);
        assertEquals("Hello World\n", result2);
        
        camel.stop();
    }
}
