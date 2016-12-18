"Jackson" Implementation
========================

* com.fasterxml.jackson.core:jackson-databind
Defines ObjectMapper which I use for Debugging output.

* com.fasterxml.jackson.core:jackson-annotations
Apparently needed if Jackson JAX-RS is used and jackson-databind is in the classpath.
Else the following Exception is thrown:
```
javax.servlet.ServletException: org.glassfish.jersey.server.ContainerException: java.lang.NoClassDefFoundError: com/fasterxml/jackson/annotation/JsonInclude$Value
	org.glassfish.jersey.servlet.WebComponent.serviceImpl(WebComponent.java:489)
	org.glassfish.jersey.servlet.WebComponent.service(WebComponent.java:427)
	org.glassfish.jersey.servlet.ServletContainer.service(ServletContainer.java:388)
	org.glassfish.jersey.servlet.ServletContainer.service(ServletContainer.java:341)
	org.glassfish.jersey.servlet.ServletContainer.service(ServletContainer.java:228)
	org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)
```
