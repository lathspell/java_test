Spring WebMVC Security
======================

Demos for Spring WebMVC with Spring Security.

Steps
-----

* A class that inherits from AbstractSecurityWebApplicationInitializer is used to register a second
ApplicationInitializer next to the WebApplicationInitializer. It registers the "springSecurityFilterChain" 
which deals with redirects to the login page and setting the cookies.
The usual WebApplicationInitializer is still needed, else no @Controller works!

* WebSecurityConfigurerAdapter should be exended. This class contains the Beans to retrieve the 
UserDetails, PasswordEncoder etc. as well as the actual URL-based access control lists.

Links
-----

* https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html
* https://docs.spring.io/spring-security/site/docs/5.1.1.RELEASE/reference/htmlsingle/
* https://spring.io/guides/gs/securing-web/
