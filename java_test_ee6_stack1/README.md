Base
====

* Java 7
* Java EE6 Web Profile
* Glassfish 3 Application Server
* (NetBeans 7 IDE)

Components
==========

* Java Server Faces (JSF)
* Primefaces

Testing
=======

## Unit-Tests
* Use the maven-surefire-plugin
* Test classes are called like src/test/FooTest.java

## Integration-Tests
* Uses the cargo-maven2-plugin to deploy the just generated .war
* Uses the maven-failsafe-plugin to test against the deployed .war
* Test classes are called like src/test/ITFooTest.java

## Frameworks
* CDI:  Arquillian / Jersey Test Framework / *CDI-Unit* / weld-se
* REST: Arquillian / *Jersey Test Framework*
* JSF:  Arquillian / JSFUnit (JBoss JSFUnit)
* JPA:  ???

Arquillian was several seconds slower than the Jersey Test Framework but the
only one that uses "real" application servers and not just their HTTP server
and Servlet engine. Yet, that might be safer anyway if the final .war is tested
once with an integration test.

See java_test_arquillian
See java_test_jersey

TODO
====
* Checkout https://github.com/mlex/jerseytest/blob/master/mjl-jersey-server/src/test/java/de/codecentric/mjl/jerseytest/helpers/FastJerseyTest.java
* Fix src/main/java/de/lathspell/stack1/frontend/rest/NotFoundMapper.java
