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

JSF Flow Scope
==============

Using http://projects.spring.io/spring-webflow/ should work fine as it even
has a org.springframework.webflow:spring-faces Maven artifact that includes
some compatibility and glue classes.
Demos are on https://github.com/spring-projects/spring-webflow-samples

JSF View Scope
==============

Using @ManagedBean together with Springs DI does not sound like a good idea.
Several implementation of a custom Spring scope that behaves like the JSF @ViewScope
exists. Some are very easy (Liferay JSF demo) and some try quite hard to avoid memory
leaks by handling ViewMapDestroyEvent etc.

Links:

* https://java.net/jira/browse/JAVASERVERFACES-3947 (affects 2.2.10, closed as invalid)
    "ViewScopeManager should better clear the view map (or publish a PreDestroyViewMapEvent) on session destroy"

* https://java.net/jira/browse/JAVASERVERFACES-3194 (closed in 2.2.8)
    "JSF 2.2 ViewScope context not destroyed when View Map is destroyed"

* https://jira.spring.io/browse/SPR-6543
* http://blog.primefaces.org/?p=702
* http://stackoverflow.com/questions/13005421/jsf-view-scope-in-spring-3-0
* http://forum.spring.io/forum/spring-projects/web/72898-view-scope-with-spring
* http://acraviolatti.blogspot.it/2013/03/jsf2-view-scope-with-spring-core.html
* `http://www.jroller.com/RickHigh/entry/adding_a_jsf_view_scope`
* http://kumarnvm.blogspot.it/2012/07/spring-3-and-jsf-2-view-scope.html
* https://github.com/weijiguang/OA/wiki/Spring-3-and-the-JSF-2-View-Scope%28spring-2%E5%8F%8AJSF-2-bean%E4%BD%9C%E7%94%A8%E5%9F%9F%29
* `https://github.com/mr-robb/agt_portal/blob/044df98f7bbd083f33aad4ce2883ecfa1288c0fc/src/main/java/mx/com/ebs/inter/scope/ViewScope.java`
* https://github.com/javaplugs/spring-jsf
* https://github.com/Zergleb/Spring-Boot-JSF-Example
* https://github.com/jirkapinkas/sitemonitoring-tutorial
* https://github.com/michail-nikolaev/primefaces-spring-scopes
* https://github.com/stephanrauh/JSF-on-Spring-Boot
* https://gist.github.com/banterCZ/4729186
