FROM openjdk:8-jre-alpine

ARG JAR_FILE
COPY target/${JAR_FILE}   /srv/foo/foo.jar
COPY target/dependency    /srv/foo/lib

CMD ["/usr/bin/java", "-cp", "/srv/foo/lib/*:/srv/foo/foo.jar", "de.lathspell.test.Application"]
EXPOSE 8080
