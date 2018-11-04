
## Spring Web

### Which modules provide Web and MVC support for Spring?
* spring-web
    - Servlet support (SpringServletContainerInitializer)
    - ApplicationContext for Servlets (WebApplicationContext, WebApplicationInitializer)
    - HTTP client, converters, filters, MIME multipart, JSON/JAXB support
    - JAX-WS support
    - JSF support
    - REST Template
    - Annotations for Web, REST, ExceptionHandler, Scopes
* spring-webmvc
    - DispatcherServlet, @EnableWebMVC, WebMvcConfigurerAdapter
    - Template support for JSP, Freemarker, Velocity, Groovy, XML etc.
    - Spring JSP Tag Library and JSP Form Tag Library

### Which are the central classes for the Spring Web Servlet lifecycle?
* DispatcherServlet which acts as a Front Controller and registers
  itself as Servlet in a standard Java Servlet Server like Tomcat.
* ContextLoaderListener

### Which classes can be used to setup Spring WebMVC as Servlet?
* `WebApplicationInitializer` must be implemented to setup an `AnnotationConfigWebApplicationContext`
  and use that to register a `DispatcherServlet` to the `ServletContext`. This replaces the web.xml
  which used to load WEB-INF/$name-servlet.xml.
* `WebMvcConfigurerAdapter` can be extended to configure ViewResolver, ResourceHandlers and other
  Spring MVC related settings.

### How is JSON mapping enabled in RestControllers?
* The Jackson Databind module is added as dependency in pom.xml

### What's the difference between @Controller and @RestController?
* The latter adds @ResponseBody which tells Spring that the return value
  of all @RequestMapping annotated methods should be converted to the
  HTTP response body.

### Name some template engines!
* Apache Velocity (deceased)
* FreeMarker (supported by Spring)
* JSP (kind of Java standard since ages)
* Thymeleaf (recommended)

### How is the current locale for message bundles determined?
* Using `LocaleResolver`.

### How is the location of message bundle property files determined?
* Using some implementation of `MessageSource`, usually the Spring
  `ReloadableResourceBundleMessageSource`.

## How many ApplicationContexts has a Spring Web MVC application at least?
* The root context which was created by the `ContextLoaderListener`
* The web context which was created by the `DispatcherServlet` and is a child of the root context
  (there could be other Servlets e.g. for static content that also inherit from the root context)
