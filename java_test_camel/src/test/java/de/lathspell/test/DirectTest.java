package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

@Slf4j
public class DirectTest {

    /**
     * Simple producer route that is used by a consumerTemplate.
     *
     * This test needs 1-2s seconds and has a delay between the point where camel is up and running and the consumer receives something. Maybe the
     * consumer is polling only every n seconds? It seems to be only 0.2s if there are .log() files in the route.
     *
     */
    @Test
    public void testProducerRoute() throws Exception {
        RouteBuilder simpleRoute = new RouteBuilder() {
            @Override
            public void configure() {
                from("file://src/main/resources/inputs/?include=input.*\\.txt&noop=true&idempotent=true&initialDelay=0&delay=1")
                        .convertBodyTo(String.class)
                        .to("direct:result");
            }
        };

        CamelContext camel = new DefaultCamelContext();
        camel.disableJMX();
        camel.setStreamCaching(true);
        camel.addRoutes(simpleRoute);
        camel.start();

        String result = camel.createConsumerTemplate().receiveBody("direct:result", String.class);
        assertEquals("Hello World\n", result);

        camel.stop();
    }

    /**
     * Simple consumer route that is used by a ProducerTemplate.
     *
     * This tests runs in 0.02s.
     */
    @Test
    public void testConsumerRoute() throws Exception {
        RouteBuilder simpleRoute = new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:getFile")
                        .pollEnrich("file://src/main/resources/inputs/?include=input.*\\.txt&noop=true&idempotent=true&initialDelay=0")
                        .convertBodyTo(String.class);
            }
        };

        CamelContext camel = new DefaultCamelContext();
        camel.disableJMX();
        camel.setStreamCaching(true);
        camel.addRoutes(simpleRoute);
        camel.start();

        String result = camel.createFluentProducerTemplate().to("direct:getFile").request(String.class);
        assertEquals("Hello World\n", result);

        camel.stop();
    }
}
