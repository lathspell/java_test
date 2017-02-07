package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

@Slf4j
public class DirectTest {

    @Test
    public void test1() throws Exception {

        RouteBuilder builder = new RouteBuilder() {
            @Override
            public void configure() {
                from("file://src/main/resources/inputs/?include=input.*\\.txt&noop=true")
                        .routeId("findFile")
                        .convertBodyTo(String.class)
                        .to("direct:result");
            }
        };

        CamelContext camel = new DefaultCamelContext();
        camel.addRoutes(builder);
        camel.start();

//        ProducerTemplate pt = camel.createProducerTemplate().
        ConsumerTemplate consumer = camel.createConsumerTemplate();
        String result = consumer.receiveBody("direct:result", String.class);
        log.info("Result: {}", result);
        consumer.stop();

        camel.stop();
    }

    @Test
    public void test2() throws Exception {
        RouteBuilder simpleRoute = new RouteBuilder() {
            @Override
            public void configure() {
                from("file://src/main/resources/inputs/?include=input.*\\.txt&noop=true")
                        .routeId("findFile")
                        .convertBodyTo(String.class)
                        .to("direct:result");
            }
        };

        CamelContext camel = new DefaultCamelContext();
        camel.addRoutes(simpleRoute);
        camel.start();

        String result = camel.createConsumerTemplate().receiveBody("direct:result", String.class);
        assertEquals("Hello World\n", result);

        camel.stop();
    }

}
