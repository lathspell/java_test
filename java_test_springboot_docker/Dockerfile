FROM alpine-java:base
MAINTAINER cb@lathspell.de
COPY target/java_test_springboot_docker-1.0-SNAPSHOT.jar    /srv/foo/
VOLUME /var/log/foo/
EXPOSE 8080
# Just an example, better get this from etcd or some other means
ENV SPRING_PROFILES_ACTIVE=dev
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/srv/foo/java_test_springboot_docker-1.0-SNAPSHOT.jar"]
