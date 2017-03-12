Exploring the mysteries of...

Spring Web (old-style with XML config)
======================================

The URL "http://localhost:8090/java_test_spring_webmvc_old/hello/" is mapped as follows:
* "/java_test_spring_webmvc_old" is the context path according to src/main/webapp/WEB-INF/context.xml or any other deployment descriptor
* "/hello" is mapped in src/main/webapp/WEB-INF/web.xml to the Spring WebMVC DispatcherServlet
* Everything below "/hello" is mapped to the HelloController bean in src/main/webapp/WEB-INF/$servletname-servlet.xml
* The view name "hello" is mapped to src/main/webapp/WEB-INF/views/hello.jsp using the InternalResourceViewResolver in $servletname-servlet.xml

I'd rather user Annotation Config :-)
