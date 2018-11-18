Spring Boot demonstration
=========================

This one shows how to use more than one Database i.e. PersistenceUnit.

The class "MultiTest" creates the same table "persons" in all databases
and checks if the right one is used. 

SpringBoot
----------

It's possible to define one DataSource in SpringBoot properties but apparently only
one. If more are needed, everything from `DataSource` over `EntityManagerFactory` to
`TransactionManager` has do be declared by hand. The `EnableJpaRepositories` annotation
has to be declared for all databases and TransactionManager etc. have to defined
specifically.

One configuration has to be annotated with `@Primary` so that the SpringBoot 
auto-configuration detects "the datasource", else `EnableJpaRepositories` does not work.

Links
-----
* https://medium.com/@joeclever/using-multiple-datasources-with-spring-boot-and-spring-data-6430b00c02e7
 