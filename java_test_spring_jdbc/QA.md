
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

### How can Transactions be done programmatically?
* By using a TransactionTemplate that's created using a provided PlatformTransactionManager.
