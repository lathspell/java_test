package de.lathspell;

public class MysqlBookstoreTest extends AbstractBookstoreTest {

    @Override
    protected String getPersistenceUnitName() {
        return "mysqlJavaTestJpaPU";
    }
}
