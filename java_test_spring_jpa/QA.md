
## Spring JPA Programming

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

### What happens to an object if only persist() and not persist+flush+refresh is called?
* Hibernate will only save it in its memory cache until the transaction is finished
  where it is forced to actually write the cached objects to the database.
* It will still call "nextval()" on the sequence generator though to fill the "id" column.
* Any database triggers (of that Hibernate does not know) will not be executed though so
  e.g. `updated_at` fields might still be empty.

### What's the difference between LocalContainerEntityManagerFactory and LocalEntityManagerFactory?
* 
* https://stackoverflow.com/questions/6156832/what-is-the-difference-between-localcontainerentitymanagerfactorybean-and-locale