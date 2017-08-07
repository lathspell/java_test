
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

### Which classes can be used to configure the Servlet part of Spring Web?
* Abstract_DispatcherServlet_Initializer
* Abstract_AnnotationConfig_DispatcherServlet_Initializer
* WebMvcConfigurerAdapter (spring-webmvc)
* WebApplicationInitializer (spring-web) as replacement for web.xml
