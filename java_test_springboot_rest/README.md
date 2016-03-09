Spring Boot REST demo application
=================================

Start de.lathspell.test.springboot.Main and go to http://localhost:8080/

Environment Profiles
--------------------

Environment profiles can be activated like:

    java -Dspring.profiles.active=dev -jar target/java_test_springboot_rest-1.0-SNAPSHOT.jar

Replacing Tomcat with Undertow or Jetty
----------------------------------------

See https://docs.spring.io/spring-boot/docs/current/reference/html/howto-embedded-servlet-containers.html#howto-use-undertow-instead-of-tomcat
