Problems
========

# CDIExtension not found

Problem:

    REST Application crashes on request with:
    java.lang.RuntimeException: javax.naming.NamingException: Lookup failed for 'com/sun/jersey/config/CDIExtension' in  SerialContext[myEnv=java.naming.factory.initial=com.sun.enterprise.naming.impl.SerialInitContextFactory, java.naming.factory.state=com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl, java.naming.factory.url.pkgs=com.sun.enterprise.naming} [Root exception is javax.naming.NameNotFoundException: CDIExtension not found]
       at com.sun.jersey.server.impl.cdi.CDIExtension.getInitializedExtension(CDIExtension.java:177)
       at com.sun.jersey.server.impl.cdi.CDIComponentProviderFactory.<init>(CDIComponentProviderFactory.java:92)
       at com.sun.jersey.server.impl.cdi.CDIComponentProviderFactoryInitializer.initialize(CDIComponentProviderFactoryInitializer.java:75)
       at com.sun.jersey.spi.container.servlet.WebComponent.configure(WebComponent.java:574)
       at com.sun.jersey.spi.container.servlet.ServletContainer$InternalWebComponent.configure(ServletContainer.java:311)
       at com.sun.jersey.spi.container.servlet.WebComponent.load(WebComponent.java:606)
       at com.sun.jersey.spi.container.servlet.WebComponent.init(WebComponent.java:208)
       at com.sun.jersey.spi.container.servlet.ServletContainer.init(ServletContainer.java:373)
       at com.sun.jersey.spi.container.servlet.ServletContainer.init(ServletContainer.java:556)
       at javax.servlet.GenericServlet.init(GenericServlet.java:244)

Cause:
    Bug?

Solution:
    Add "-Dcom.sun.jersey.server.impl.cdi.lookupExtensionInBeanManager=true" to
    the Glassfish server-config -> JVM-Options



# Authentication failed with password from login store

Problem:
    CLI commands using "asadmin" failed with the above error message.

Cause:
    Wrong or no password in $HOME/.asadminpass

Solution:
    Change password with "asadmin login". Maybe remove old file before.



# Authentication failed with password from login store

Problem:
    CLI commands using "asadmin" failed with the above error message.

Cause:
    Wrong or no password in $HOME/.asadminpass

Solution:
    Change password with "asadmin login". Maybe remove old file before.



# @Inject does not inject anything in a REST webservice

Problem:
    @Inject does not inject anything in a REST webservice

Cause:
    Not yet fully understood.

Solution:
    Add @RequestScoped or probably any other CDI scope annotation.



# java.lang.NoSuchMethodError: org.glassfish.hk2.api.ServiceLocator.setNeutralContextClassLoader(Z)V

Problem:

    getClientResponse(de.lathspell.java_test_ee6_stack1.frontend.rest.MyRestApiTest)  Time elapsed: 0.012 sec  <<< ERROR!
    java.lang.NoSuchMethodError: org.glassfish.hk2.api.ServiceLocator.setNeutralContextClassLoader(Z)V
            at org.glassfish.jersey.internal.inject.Injections._createLocator(Injections.java:144)
            at org.glassfish.jersey.internal.inject.Injections.createLocator(Injections.java:137)
            at org.glassfish.jersey.server.ApplicationHandler.<init>(ApplicationHandler.java:272)
            at org.glassfish.jersey.test.JerseyTest.<init>(JerseyTest.java:140)
            at de.lathspell.java_test_ee6_stack1.frontend.rest.MyRestApiTest.<init>(MyRestApiTest.java:44)

Cause:
The pom.xml contained two org.glassfish.jersey.test-framework.providers
artifacty, one with version 2.4 and one with 2.3.1.

Solution:
The 2.3.1 version artifact was removed.



# File encoding has not been set

Problem:
File encoding has not been set, using platform encoding UTF-8, i.e. build is platform dependent!

Cause:
The surefire and failsafe plugins do not use project.build.sourceEncoding.

Solution:
Adding this to project.properties:
"<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>"
