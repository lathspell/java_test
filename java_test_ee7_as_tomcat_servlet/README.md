Overview
========
Running a JavaEE application on a plain Serverlet Container like Tomcat.

All necessary API implementations are added as explicit Maven depedency if
the "RunAsServlet" Maven Profile is activated.

Tested APIs
===========

| API        | Description          | Implementation      | Status |
| ---------- | -------------------- | ------------------- | ------ |
| CDI        | Dependency Injection | JBoss WELD          | OK     |
| JAX-RS     | REST                 | Oracle Jersey       | OK     |       
| JSF        | Java Server Faces    | Oracle Mojarra      | OK     |
| JPA        | SQL ORM              | Hibernate Core      | OK     |
| Validation | JavaBean validation  | Hibernate Validator | OK     |
| JSON       | JSON conversion      | Jackson             | OK     |
| Logging    | SLF4J Logging        | Logback             | OK     |
| SQL        | SQL Binding          | Postgres            | OK     |

Limitations:
* @ViewScoped does not yet work!

Remarks:
* "Jersey": Needs jersey-cdi1x to use CDI and not only HK2
* "Mojarra" Needs runtime dependency to jsf-api to get LogStrings.properties 
  which is not in javaee-api-7.0, see README.Caveats.md.
* "Postgres" must be added as runtime dependency if a local Persistence Unit
  is defined in persinstence.xml. Alternatively it can be put into Tomcat's
  lib/ directory if a Persitence Unit is defined in server.xml or context.xml.

Tomcat Configuration
====================

Add to Tomcat conf/context.xml to have it available for all contexts:

```
  <Resource name="ds/JavaTestDS" auth="Container"
          type="javax.sql.DataSource" driverClassName="org.postgresql.Driver"
          url="jdbc:postgresql://localhost/java_test"
          username="java_test" password="java_test" maxTotal="20" maxIdle="10" maxWaitMillis="10000"/>
```

Further information
===================

See README.Caveats.md for a descriptions of some problems that occured.
