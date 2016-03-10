Spring Boot demonstration
=========================

This project aims to integrate many different Spring modules and libraries
into one big application.

Start de.lathspell.test.springboot.Application and go to http://localhost:8080/

REST Server
-----------
In the .web.rest package there are a Spring Web MVC and a JAX-RS annotated REST
resource coexisting peacefully.

Environment Profiles
--------------------

Environment profiles like src/main/resources/application-dev.yml can be activated like:

    java -Dspring.profiles.active=dev -jar target/java_test_springboot_rest-1.0-SNAPSHOT.jar

Replacing Tomcat with Undertow or Jetty
----------------------------------------

Can be done in pom.xml. Remember to adjust server specific settings in application.yml!
See https://docs.spring.io/spring-boot/docs/current/reference/html/howto-embedded-servlet-containers.html#howto-use-undertow-instead-of-tomcat
