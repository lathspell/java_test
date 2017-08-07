Spring Boot demonstration
=========================

This demo shows a REST server that reports problems using the Zalando
implementation of RFC 7807 "Problem Details for HTTP APIs".

Start de.lathspell.test.springboot.Main and go to http://localhost:8080/

    http://localhost:8080/my/doesnotexist   - NoHandlerException    => 404 Not Found
    http://localhost:8080/my/tea            - OutOfTeaException     => 503 Service Unavailable
    http://localhost:8080/my/crash          - RuntimeException      => 500 Internal Server Error
    http://localhost:8080/my/hello          - ValidationException   => 400 Bad Request
    http://localhost:8080/my/hello?name=Tim - No Exception!         => 200 OK

Links
=====
* http://www.baeldung.com/exception-handling-for-rest-with-spring
* https://github.com/zalando/problem-spring-web
* https://github.com/zalando/problem
