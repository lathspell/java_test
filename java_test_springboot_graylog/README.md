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
* `http://localhost:8080/`              - Static index.html
* `http://localhost:8080/hello`         - REST Resource that also emits logging
* `http://localhost:9000`               - Graylog (admin/admin)

Caveats:
--------

* Graylog GELF TCP does not support compression. Use GELF UDP!

Usage:
======

* Graylog "Extractors" can be used to e.g. extract the temperature as numeric
  value from all Strings matching /^Temperature: (\d+)$/ and store them as
  distinct values.

Links
=====

