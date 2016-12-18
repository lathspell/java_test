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

Remarks:
* "Jersey": Needs jersey-cdi1x to use CDI and not only HK2
* "Mojarra" Needs runtime dependency to jsf-api to get LogStrings.properties 
  which is not in javaee-api-7.0, see README.Caveats.md.
* "Postgres" must be added as runtime dependency as 

Further information
===================

See README.Caveats.md for a descriptions of some problems that occured.
