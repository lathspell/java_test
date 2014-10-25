package de.lathspell;

public class PostgresBookstoreTest extends AbstractBookstoreTest {

    @Override
    protected String getPersistenceUnitName() {
        return "postgresJavaTestJpaPU";
    }
}
