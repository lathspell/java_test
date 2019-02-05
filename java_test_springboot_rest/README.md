Spring Boot demonstration
=========================

This demo shows a REST server that uses both, Spring Web MVC and JAX-RS
annotated classes.

Start
-----

Start de.lathspell.test.springboot.Main or "mvn spring-boot:run" and go to http://localhost:8080/

Caveats
-------

If spring.main.allow-bean-definition-overriding=true ist not set in application.properties,
the following error comes when starting:

    Description:
    The bean 'requestContextFilter', defined in class path resource [org/springframework/boot/autoconfigure/web/servlet/WebMvcAutoConfiguration$WebMvcAutoConfigurationAdapter.class], could not be registered. A bean with that name has already been defined in class path resource [org/springframework/boot/autoconfigure/jersey/JerseyAutoConfiguration.class] and overriding is disabled.
    Action:
    Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true
