Spring Boot with Prometheus/Grafana Monitoring
==============================================

This demo shows a REST server that is monitored by Prometheus and
has a nice Grafana dashboard.

Setup
=====

Docker Compose
--------------

Run `docker-compose up`. The necessary config is in `docker-compose.yml`.
Some daemon configuration is in this directory as well, see Docker Compose
configuration.

Springboot Prometheus endpoint
------------------------------

Enable `management.endpoints.web.exposure.include: prometheus` in application.yml.

Then add the following Maven depenedencies:
    org.springframework.boot:spring-boot-starter-actuator
    io.micrometer:micrometer-registry-prometheus

View
----

Finally start de.lathspell.test.springboot.Main and go to 
* `http://localhost:8080/`                      - Static index.html with links
* `http://localhost:8080/actuator/prometheus`   - Prometheus compatible metrics export

Caveats:
========

* The Grafana datasource URL must be `http://prometheus:9090` not
  `http://localhost:9090` or else `docker-proxy` won't find it.

Links
=====

* https://finestructure.co/blog/2016/5/16/monitoring-with-prometheus-grafana-docker-part-1
* https://www.igorski.co/java/spring-boot/spring-boot-metrics-prometheus/
* https://github.com/vegasbrianc/prometheus
