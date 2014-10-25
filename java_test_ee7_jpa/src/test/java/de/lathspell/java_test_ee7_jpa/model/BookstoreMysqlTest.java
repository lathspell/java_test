package de.lathspell.java_test_ee7_jpa.model;

public class BookstoreMysqlTest extends BookstoreTemplate {

    @Override
    protected Pu getPersistenceUnitName() {
        return Pu.mysqlJavaTestJpaPU;
    }
}
