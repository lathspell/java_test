package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.ProxyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

@Slf4j
public class ProxyBuilderTest {

    @Test
    public void test1() throws Exception {
        RouteBuilder simpleRoute = new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:getFile")
                        .pollEnrich("file://src/main/resources/inputs/?include=input.*\\.txt&noop=true&idempotent=false&initialDelay=0")
                        .convertBodyTo(String.class);
            }
        };

        CamelContext camel = new DefaultCamelContext();
        camel.addRoutes(simpleRoute);
        camel.start();

        HelloService service = new ProxyBuilder(camel).endpoint("direct:getFile").build(HelloService.class);
        assertEquals("Hello World\n", service.submitStringReturnString(""));
        assertEquals("Hello World\n", service.returnString());

        camel.stop();
    }

}

interface HelloService {

    public String returnString();

    public String submitStringReturnString(String s);
}
