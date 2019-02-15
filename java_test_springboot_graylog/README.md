Spring Boot with Graylog
========================

This demo shows a REST server that exports Logfiles to Graylog.

Setup
=====

Docker Compose
--------------

Run `docker-compose up`. The necessary config is in `docker-compose.yml`.
Some daemon configuration is in this directory as well.

View
----

Start with "mvn spring-boot:run" and go to 
* `http://localhost:8080/`                      - Static index.html with links
* `http://localhost:9000`                       - Graylog

Caveats:
========

Links
=====

