
## Spring JDBC Programming


### What is needed for a JDBC database connection
* A driver on the classpath
* An URL and credentials to create da DataSource object

### What is the central helper class that Spring offers for JDBC?
* JdbcTemplate

### What is the difference between SimpleDriverDataSource and DataSource
* SimpleDriverDataSource is no connection pool, it opens a new connection per instance.

### What can be used to initialize a database?
* DatabasePopulatorUtils.execute() or a DataSourceInitializer bean
