Spring Boot @Async demonstration
================================

This one shows how to write methods that run asynchronously in their own Threads.

AsyncStarter implements ApplicationListener so that its onApplicationEvent gets
started after SpringBoot has finished initializing.

This method then calls `run()` on an instance of `MyTimer`. That method is
annotated with @Async and thus started in a separate thread.

While `run()` does it's endless loop, the application is still capable of
serving e.g. REST requests in its normal threads.

Links:
* https://spring.io/guides/gs/async-method/
* https://www.baeldung.com/spring-async
* https://www.e4developer.com/2018/03/30/introduction-to-concurrency-in-spring-boot/
