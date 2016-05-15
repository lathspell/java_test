Integrating JSF with Spring
===========================

Spring into Java EE or JSF into Spring?
---------------------------------------

* Variant 1 - Use Spring context configuration in a Java EE application

The application is a regular Java EE application that runs inside an application
server.
Some Spring classes are registered in web.xml as `<listener-class>` so that they
can load their configuration from application.xml at application start.
Another Spring class is registered in faces-config.xml as `<el-resolver>` so 
that bean references in EL notation like `${foo}` are first asked from Spring
and then from JSF (the latter refers to the deprecated `@ManagedBean` annotation,
modern Java EE apps should use CDI anyway!).

* Variant 2 - Use JSF in a Spring/Spring Boot application (this project)

The application is a regular Spring or Spring Boot application that runs as
fat jar without an application server.
Configuration is not an issue here as it's loaded as usual by Spring.
Just like Variant 1 a special Spring class is registered as `<el-resolver>` so
that the JSF module finds the referenced Java Beans.

Links
-----

* http://www.journaldev.com/7112/jsf-spring-integration-example-tutorial
* http://www.beyondjava.net/blog/integrate-jsf-2-spring-3-nicely/
* http://docs.spring.io/autorepo/docs/spring/4.2.x/spring-framework-reference/html/web-integration.html

Caveats
-------

* Mojarra: Needs com.sun.faces.forceLoadConfiguration=true parameter
* Mojarra: Needs a JSP implementation like Tomcat Jasper, otherwise no "Initializing Mojarra 2.2.13 ... for context ..." message and Exception

* MyFaces: Gives the following exception on startup:

```
    There was an unexpected error (type=Internal Server Error, status=500).
    No Factories configured for this Application. This happens if the faces-initialization does not work at all - make sure that you properly include all configuration settings necessary for a basic faces application and that all the necessary libs are included. Also check the logging output of your web application and your container for any exceptions! If you did that and find nothing, the mistake might be due to the fact that you use some special web-containers which do not support registering context-listeners via TLD files and a context listener is not setup in your web.xml. A typical config looks like this; <listener> <listener-class>org.apache.myfaces.webapp.StartupServletContextListener</listener-class> </listener> 
```
