Running SpringBoot with JSF as Servlet on Tomcat
================================================

SpringBoot as Servlet
---------------------

SpringBoot can perfectly well run as plain Servlet on an external Servlet
Container like Tomcat.
It can be started either programmatically using SpringBootServletInitializer or
explicitly in web.xml which is part of the Servlet spec and therefore of course
be fully usable.

Necessary modifications:
* Application class has no Main method but is extending `SpringBootServletInitializer`.
  The mandatory override method sets up SpringBoot.
* In pom.xml the project type has to be changed from `jar` to `war`.
* In pom.xml the `spring-boot-starter-web` dependency has a dependency to
  `spring-boot-starter-tomcat`. This dependency has to be declared as `provided` so
  no Embedded Tomcat is added to the .war and produces class conflicts with the
  external one.
* Netbeans seems to require a META-INF/context.xml for if Tomcat is used.
  It does autogenerate it for you though.

SpringBoot with JSF
-------------------

JSF is technically a second, independent, Servlet that registeres for any
XHTML files. It does however need to access the JSF Backing Beans that are
referenced via the Expression Language (EL) like e.g. `${foo}`. As the default
method of CDI is not available, a special Spring EL Resolver is registered in
faces-config.xml so that all Spring @Component object can be used.

Necessary modifications:
* In pom.xml the JSF API and Implementation (Mojarra or MyFaces) must be added
* Start Faces Servlet in web.xml and map it to `*.xhtml`
* Add faces-config.xml and register SpringBeanFacesELResolver as `el-resolver`
