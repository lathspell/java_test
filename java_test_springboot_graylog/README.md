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

CLI Viewer
==========

There is one Python script that attempts to provide some "tail -f" style logging.
It is a bit unfinished/buggy though:
 - does not support Python formats out of the box (or can't extract the field
   names if formats are used)
 - tls=false does not work in config
 - errors in the filter just result in nothing printed
 - so far no colors

Patched, it can be used like this:

    $ glogcli -u admin -p admin --no-tls --stream=5c6c814ee64ada000a805e85  -f  --format-template=my-temp --fields=timestamp,message,window,level,logger,source,app_name,logger_name,temperature_extracted 'temperature_extracted:>20'
    2019-02-25 23:05:14.389 INFO backend@localhost [      left] de.lathspell.test.services.Thermometer: Temperature 44
    2019-02-25 23:05:15.389 INFO backend@localhost [     right] de.lathspell.test.services.Thermometer: Temperature 49
    2019-02-25 23:05:17.389 INFO backend@localhost [     right] de.lathspell.test.services.Thermometer: Temperature 40
    2019-02-25 23:05:18.389 INFO backend@localhost [     right] de.lathspell.test.services.Thermometer: Temperature 40

Configuration format:

    [format:my-temp]
    format={timestamp} {level} {app_name}@{source} [{window:>10}] {logger_name}: {message}

Download at https://github.com/globocom/glog-cli

Links
=====


