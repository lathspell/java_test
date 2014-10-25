package de.lathspell.java_test_ee7_jpa.model;

public class BookstorePostgresTest extends BookstoreTemplate {

    @Override
    protected Pu getPersistenceUnitName() {
        return Pu.postgresJavaTestJpaPU;
    }
}
