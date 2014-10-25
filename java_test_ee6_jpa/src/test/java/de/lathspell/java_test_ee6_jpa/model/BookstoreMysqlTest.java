package de.lathspell.java_test_ee6_jpa.model;

public class BookstoreMysqlTest extends BookstoreTemplate {

    @Override
    protected Pu getPersistenceUnitName() {
        return Pu.mysqlJavaTestJpaPU;
    }
}
