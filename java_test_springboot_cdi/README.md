Spring vs. CDI
==============

Status of Spring
----------------

(4.3.0 as of 2016-03)

Spring honors JSR-330 but not JSR-299 i.e. you can use @Inject and @Named but not @RequestScoped or @Dependent!

All beans are considered "Singleton" as it is the default for Spring beans.
See: http://docs.spring.io/spring/docs/4.2.5.RELEASE/spring-framework-reference/html/beans.html#Limitations-of-the-standard-approach

The API is available via the "javax.inject" Maven artefact.


Specs
-----

* JSR 250: Common Annotations for the Java Platform (2006)
    @Resource
    @PostConstruct
    @PreDestroy
    @ManagedBean

* JSR-299: Contexts and Dependency Injection for the JavaTM EE platform (2011)
    Is based on JSR-330
    @Model
    @Alternative
    @Dependent
    @RequestScoped
    @ApplicationScoped
    ...

* JSR-330: Dependency Injection for Java
    @Inject
    @Qualifier
    @Scope
    @Singleton
    @Named
    @Provider

* JSR-346: Contexts and Dependency Injection for JavaTM EE 1.1
    Mostly clarifications

* JSR-365: Contexts and Dependency Injection for JavaTM EE 2.0
    Java SE bootstrap API via CDIProvider
    Asynchronous events
