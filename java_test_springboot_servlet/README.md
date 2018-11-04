Spring Boot running on external Tomcat
======================================

1. In `pom.xml` change `packaging` from `jar` to `war`.
2. In `pom.xml` add the following to that no Tomcat runtime files are included in the resulting `war` file:
    ```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-tomcat</artifactId>
        <scope>provided</scope>
    </dependency>
    ```
3. The `@SpringBootApplication` annotated class must extend `SpringBootServletInitializer` to conform to 
   the Servlet 3.0 specification which demands either a `ServletInitializer` or a `web.xml` on the 
   classpath. Of course there is no need for a `public static void main()` method in this scenario.

Hints:
* Don't forget to add `scanBasePackages` to `@SpringBootApplication` to get `@ComponentScan`!
