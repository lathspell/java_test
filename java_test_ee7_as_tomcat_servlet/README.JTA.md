Problems
========

To make @ResourceContext available one needs a JNDI server, an JTA 
implementation like Atomikos or Narayana and a lot of bootstrap code.
I've not yet found a simple jar that could be included and just provide
everything.

Fundamentals
============

Entity Manager and Entity Manager Factory
-----------------------------------------

Quoting from http://docs.jboss.org/hibernate/stable/entitymanager/reference/en/html/transactions.html 

 A EntityManagerFactory is an expensive-to-create, threadsafe object intended to 
 be shared by all application threads. It is created once, usually on application startup.
 
 An EntityManager is an inexpensive, non-threadsafe object that should be used once, 
 for a single business process, a single unit of work, and then discarded. 
 An EntityManager will not obtain a JDBC Connection (or a Datasource) unless it is needed,
 so you may safely open and close an EntityManager even if you are not sure that data
 access will be needed to serve a particular request.

Managed and Unmanaged environment
---------------------------------

Quoting from http://docs.jboss.org/hibernate/stable/entitymanager/reference/en/html/transactions.html

In a non-managed environment, an EntityManagerFactory is usually responsible for 
its own database connection pool. The application developer has to manually set
transaction boundaries, in other words, begin, commit, or rollback database 
transactions itself.

```
// Non-managed environment idiom
EntityManager em = emf.createEntityManager();
EntityTransaction tx = null;
try {
    tx = em.getTransaction();
    tx.begin();

    // do some work
    ...

    tx.commit();
} catch (RuntimeException e) {
    if (tx != null && tx.isActive()) {
        tx.rollback();
    }
    throw e;
} finally {
    em.close();
}
```

A managed environment usually provides container-managed transactions, with the 
transaction assembly defined declaratively through annotations of EJB session beans,
for example. Programmatic transaction demarcation is then no longer necessary, 
even flushing the EntityManager is done automatically.

Quoting from http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#bootstrap

```
For compliant container-bootstrapping, the container will build an EntityManagerFactory 
for each persistent-unit defined in the META-INF/persistence.xml configuration file and
make that available to the application for injection via the 
javax.persistence.PersistenceUnit annotation or via JNDI lookup.
```

That part is missing in my examples and seems not to be available by just adding
a dependency with some Provider.

