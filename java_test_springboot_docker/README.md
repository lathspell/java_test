Spring Boot demonstration
=========================

This demo shows a REST server that runs in a Docker container.

The docker image is build using "docker build", not by a Maven plugin.
See `java_test_springboot_docker2` for a different approach.

The application is called "foo", runs from a jar file and
is configured to write a logfile to /var/log/foo/.
It listens on port 8080 for HTTP requests.


Further Reading
===============

See `docker_test` project.


Noteworthy Docker images
========================

* alpine                        - Official Alpine Linux; minimalistic distro with only 5MB or so
* maven:3-jdk-8-alpine          - Official Maven 3.x with JDK8 and Alpine Linux
* openjdk:8-jdk-stretch         - Official OpenJDK for JDK8 and Debian "stretch" Linux
* openjdk:8-jdk-slim-stretch    - Official OpenJDK for JDK8 and stripped down Debian "stretch" Linux
* openjdk:8-jdk-alpine          - Official OpenJDK for JDK8 and Alpine Linux

Maven Plugins
=============

* https://github.com/spotify/dockerfile-maven   - Simply calls "docker build" from Maven
* https://dmp.fabric8.io/ - More sophisticated, can run integration-tests in Docker

A completely different idea is to run the actual build process including the tests
inside a Docker container. That way build slaves could be kept stupid Docker hosts
and would not need to have the right JDK or Maven version!
See https://codefresh.io/howtos/using-docker-maven-maven-docker/


Build and Run
=============

Initial base image (optional)
-----------------------------

Usually the openjdk:8-jdk-alpine image is just fine but we can build our own:

Create docker image with Linux and Java runtime. This has to be done only
once or whenever an update of Alpine linux or the JRE is necessary.

The trailing dot tells docker to look in the current directory for its
Dockerfile and as base path for COPY.

The `--rm=true` just removes intermediate images.

    docker build -f Dockerfile.alpine-java --tag=lathspell/alpine-java:latest --rm=true .


Docker volume for log files
---------------------------

One way to make logfiles available outside the Docker image is to create
so called "volumes" which are just directories that are mapped (bind mounted)
into the container. This step has to be done once per docker installation.

    docker volume create --name=foo-logs

This is probably not necessary as one can mount a host directory directly
with "-v" at "docker run".

Also read https://docs.docker.com/config/containers/logging/

Docker image for the application
--------------------------------

Build the jar file. The springboot-maven-plugin creates a fat jar with
all dependencies.

    mvn package

Build the Docker image from the previously created image that combined Alpine
Linux and Java and the just created jar file.

    docker build --tag=foo:latest --rm=true .

Run
---

The Dockerfile only contains a recipe for building an image. When running it,
the port and directory mappings also have to be specified. The "-d" detach the
console from the container.
The "volume" maps the container directory /var/log/foo to the directory /tmp/foo-logs/
on the host (or any Docker volume).

    docker run -d --name=foo -p 8320:8080 --volume=/tmp/foo-logs/:/var/log/foo foo:latest

Without further options it runs in foreground mode.
With "-d" it runs as daemon i.e. with no output on the current shell.
With "-it" it runs as daemon with "interactive tty" i.e. output is visible and
the Docker specific escape ctrl-p+q is necessary to detach from the container
while still keeping it running (same as with later "docker attach foo").

Test
----

    curl http://localhost:8320/rest/mvc/

