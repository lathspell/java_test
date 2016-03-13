Spring Boot demonstration
=========================

This demo shows a non-web application that parses command line arguments.

Start with

        mvn package
        ./target/java_test_springboot_cli-*.jar --hello=Max
    
or

        mvn package
        java -jar target/java_test_springboot_cli-*.jar --hello=Max

or

        mvn spring-boot:run -Drun.arguments="--hello=James"

or
    
        mvn exec:java -Dexec.mainClass=de.lathspell.test.Cli -Dexec.arguments="--hello=Max"

Links
-----

* http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#howto-create-a-non-web-application

Logging
-------

As this project should only use spring.log for logging and leave stdout
exclusively to System.out.println() by the application, we have to define
a custom src/main/resources/logback.xml that does not include a CONSOLE logger!
Log levels can still be defined in src/main/resources/application.yml.

