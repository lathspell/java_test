## Spring JPA Programming

### History of JPA
* JPA 2.0 (JSR 317) from 2007 for Java EE 6
* JPA 2.1 (JSR 338) from 2013 for Java EE 7

### What are the main JPA classes
* PersistenceContext - ???
* EntityManager - an object that has about the lifetime of a transaction
* EntityManagerFactory - thread safe shared objects that create EntityManagers
* PersistenceUnit - a set of domain objects aka entities

### Some JPA implementations
* Hibernate (used by RedHat JBoss and Spring)
* EclipseLink (used by Oracle Glassfish)
* Apache OpenJPA (used by Apache TomEE, IBM Weblogic and Websphere)
* Data Nucleus (used by Google App Engine)

### Caching Concepts

JPA has its own caching layers. Some queries can be done without actually
accessing the database. That also means that great care has to be taken when
data is modified externally as it might become necessary to explicit forced JPA
to load the current data from database using em.refresh()! This is even true
for simple things like auto incremented primary keys or timestamp columns that
are filled by SQL triggers.

### What happens to an object if only persist() and not persist+flush+refresh is called?
* Hibernate will only save it in its memory cache until the transaction is finished
  where it is forced to actually write the cached objects to the database.
* It will still call "nextval()" on the sequence generator though to fill the "id" column.
* Any database triggers (of that Hibernate does not know) will not be executed though so
  e.g. `updated_at` fields might still be empty.

### What is JPQL?
The Java Persistence Query Language (JPQL) is a substandard of JPA and defines
a query language that looks very much like SQL but is slightly different as it
operates on classes and not tables (e.g. "Book b inner join b.authorId a").
See <https://en.wikipedia.org/wiki/Java_Persistence_Query_Language>

### What are the two JPA Persistence-Unit Transaction-Types compared to Spring?

RESOURCE_LOCAL
* All transactions refer to exactly one resource like e.g. a JDBC DataSource
* Use EntityManagerFactory to get EntityManager instances
* Use @PersistenceUnit to inject an EntityManagerFactory, never @PersistenceContext!
* Every EntityManager must use Transactions which one gets via em.getTransaction()

JTA:
* All transactions refer to a global JTA transaction which can span multiple DataSources
* Directly use the provided EntityManager, never EntityManagerFactory 
* Use @PersistenceContext to inject an EntityManager, never @PersistenceUnit!
* @Transactional can be used on a method
* An injected UserTransaction object may be used to created and offers explicit begin(), commit() etc.

Spring:
* All transactions are handled by Spring and can span multiple DataSources
* Use PlatformTransactionManager

    *TODO*

### Which drawbacks has JTA?

* It needs a JTA implementation like Atomikos or a Java EE application server
* It needs a JNDI implementation or a Java EE application server
* It usually requires a data source from an application server and not from a DataSource

### Which advantages has Springs Transaction-Type?

* It's global across multiple data sources
* It has consistent exception handling as it maps the exceptions of the underlying data sources
* It uses unchecked exceptions

### Which four things are defined in a TransactionDefinition?
* Isolation level (DEFAULT, READ_COMMITTED, READ_UNCOMMITTED, REPEATABLE_READ, SERIALIZABLE)
* Propagation (MANDATORY, NEVER, REQUIRED, ...)
* Timeout
* Read-only status (for optimization)

### Define the isolation levels
* DEFAULT -
* READ_COMMITTED - 
* READ_UNCOMMITTED -
* REPEATABLE_READ - 
* SERIALIZABLE - 

    **see java_test_spring_jdbc/QA.md**

### Define the propagation levels
* MANDATORY - 
* NEVER - 
* REQUIRED - 

    **see java_test_spring_jdbc/QA.md**

### What's the difference between LocalContainerEntityManagerFactory and LocalEntityManagerFactory?
* TODO
* https://stackoverflow.com/questions/6156832/what-is-the-difference-between-localcontainerentitymanagerfactorybean-and-locale

### Caveats
* Spring's @Transactional depends on AOP. Therefore @Transactional must be on a method that itself 
  calls public methods of the DAO or JpaRepository class. If it would call a private method of it's own
  class, the AOP mechanism could not intercept that call as it only works on public members.
* JpaRepository methods like delete() should be followed by flush if one expectes them to also delete 
  rows that were already present in the table and not inserted by this very TransactionManager.
  Else they get "optimized away" (*at least it seemed that way*).
