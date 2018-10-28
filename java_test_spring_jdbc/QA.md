
## Spring JDBC Programming

### What stands ACID for?
* Atomicity (a transaction either succeeds or fails)
* Consistency (after a transaction finished the database is consistent in regard of e.g. foreign keys and constraints)
* Isolation (multiple transactions can be run simultaneously without interfering)
* Durability (a transaction only finishes if all its data has been persistet on a non-volatile storage medium)

### What is needed for a JDBC database connection
* A driver on the classpath
* An URL and credentials to create da DataSource object

### What is the central helper class that Spring offers for JDBC?
* JdbcTemplate

### What is the difference between SimpleDriverDataSource and DataSource
* SimpleDriverDataSource is no connection pool, it opens a new connection per instance.

### What can be used to initialize a database?
* DatabasePopulatorUtils.execute() or a DataSourceInitializer bean

### Can @Transactional only be used on method level or also on class level?
* On class level it makes all methods @Transactional although that can be overriden.

### Should @Transactional be applied to the Service layer, the Repository or both?
* Usually at the Service layer as multiple Repositories may be accessed in the same transaction.
* If there is no Service layer then at the Repositories.
* Avoid using it for both!

### How can Transactions be done programmatically i.e. without @Transactional?
* By using PlatformTransactionManager with TransactionStatus
* By using PlatformTransactionManager with a TransactionTemplate

### The four flavours how Transactions with @Transactional can be done in Spring
* Local JDBC Spring environment - `DataSourceTransactionManager` and `JdbcTemplate`
* Local Hibernate Spring environment - `HibernateTransactionManager`and `LocalSessionFactoryBean`
* Local JPA Spring environment - `JpaTransactionManager` and `LocalContainerEntityManagerFactoryBean`
* Enterprise JTA Spring environment - `JtaTransactionManager` and JNDI provided datasource from an AppServer

## What are the Transaction Propagation levels?
* REQUIRED - An existing transaction will be used or a new one created
* REQUIRES_NEW - A new transaction will be started and any existing suspended.
* NESTED - A transaction inside another transaction will be used
* MANDATORY - An existing transaction must be used or Exception
* NEVER - No transaction may be used or Exception
* NOT_SUPPORTED - No transaction will be used, existing suspended
* SUPPORTS - A transaction might be used, if none exists the method does not used one

## What are the Transaction Isolation Levels?
* DEFAULT - Uses the DBMS default (READ_COMMITTED for PostgreSQL and Oracle, REPEATABLE_READ for MariaDB)
* READ_UNCOMMITTED - Dirty Reads, Nonrepeatable Reads, Phantom Reads and Serialization Anomaly possible
* READ_COMMITTED - Nonrepeatable Reads, Phantom Reads and Serialization Anomaly possible
* REPEATABLE_READ - Phantom Reads and Serialization Anomaly possible
* SERIALIZABLE - Serialization Anomaly possible
