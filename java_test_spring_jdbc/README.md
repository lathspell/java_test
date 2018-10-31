
Spring JDBC Programming
=======================

Packages
--------

* common    - Model classes and database schema used by all tests
* test2     - simple, XML Configuration (Spring 1.0)
* test3     - simple, Annotation Configuration (Spring 2.5)
* test4     - simple, Java Configuration (Spring 3.0)
* test5     - builtin BeanPropertyRowMapper instead of custom RowMapper 
* test6     - transactions in programmatic style with PlatformTransactionManager
* test7     - transactions using TransactionTemplate
* test8     - transactions with @Transactional and JavaConfig (*preferred*)
* test9     - transactions with @Transactional and XML-Config
* test10    - Repositories
* test11    - SimpleJdbcInsert

JDBC Tests with "transactions" usually use`DataSourceTransactionMangager`,
called "Local JDBC Spring environment" in the book