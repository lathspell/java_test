
Build
=====

Devel
-----

    ./gradlew bootRun

Docker
------
    ./gradlew dockerBuildImage


Features
========

OpenTracing
-----------

Jaeger is started via `docker-composer up`. Events are send on port 127.0.0.1:6831/UDP by the Jaeger client that
was initialized with defaults and settings from Env and application.properties by "opentracing-spring-jaeger-web-starter".

