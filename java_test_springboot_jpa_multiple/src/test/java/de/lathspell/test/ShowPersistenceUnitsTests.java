package de.lathspell.test;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@Slf4j
public class ShowPersistenceUnitsTests {

    @PersistenceContext(unitName = "derbyPU")
    private EntityManager emDerby;

    @PersistenceContext(unitName = "h2PU")
    private EntityManager emH2;

    @PersistenceContext(unitName = "hsqlPU")
    private EntityManager emHsql;

    @Autowired
    @Qualifier("derbyEMF")
    private EntityManagerFactory derbyEmf;

    @Autowired
    @Qualifier("h2EMF")
    private EntityManagerFactory h2Emf;

    @Autowired
    @Qualifier("hsqlEMF")
    private EntityManagerFactory hsqlEmf;

    @Autowired
    @Qualifier("derbyDS")
    private DataSource derbyDS;

    @Autowired
    @Qualifier("h2DS")
    private DataSource h2DS;

    @Autowired
    @Qualifier("hsqlDS")
    private DataSource hsqlDS;

    @Autowired
    @Qualifier("derbyDSProps")
    private DataSourceProperties derbyDsProps;

    @Autowired
    @Qualifier("h2DSProps")
    private DataSourceProperties h2DsProps;

    @Autowired
    @Qualifier("hsqlDSProps")
    private DataSourceProperties hsqlDsProps;

    @Test
    public void testDataSourcesProperties() throws Exception {
        assertEquals("org.apache.derby.jdbc.EmbeddedDriver", derbyDsProps.getDriverClassName());
        assertEquals("org.h2.Driver", h2DsProps.getDriverClassName());
        assertEquals("org.hsqldb.jdbc.JDBCDriver", hsqlDsProps.getDriverClassName());
    }

    @Test
    public void testDataSources() throws Exception {
        assertEquals("Apache Derby Embedded JDBC Driver", derbyDS.getConnection().getMetaData().getDriverName());
        assertEquals("H2 JDBC Driver", h2DS.getConnection().getMetaData().getDriverName());
        assertEquals("HSQL Database Engine Driver", hsqlDS.getConnection().getMetaData().getDriverName());
    }

    @Test
    public void testEMF() throws Exception {
        assertEquals("org.hibernate.dialect.DerbyTenSevenDialect", derbyEmf.unwrap(SessionFactory.class).unwrap(HibernateEntityManagerFactory.class).getSessionFactory().getDialect().toString());
        assertEquals("org.hibernate.dialect.H2Dialect", h2Emf.unwrap(SessionFactory.class).unwrap(HibernateEntityManagerFactory.class).getSessionFactory().getDialect().toString());
        assertEquals("org.hibernate.dialect.HSQLDialect", hsqlEmf.unwrap(SessionFactory.class).unwrap(HibernateEntityManagerFactory.class).getSessionFactory().getDialect().toString());
    }

    @Test
    @Transactional(transactionManager = "derbyTM")
    public void testDerby() throws Exception {
        assertEquals("Apache Derby Embedded JDBC Driver", emDerby.unwrap(SessionImpl.class).connection().getMetaData().getDriverName());
    }

    @Test
    @Transactional(transactionManager = "h2TM")
    public void testH2() throws Exception {
        assertEquals("H2 JDBC Driver", emH2.unwrap(SessionImpl.class).connection().getMetaData().getDriverName());
    }

    @Test
    @Transactional(transactionManager = "hsqlTM")
    public void testHsql() throws Exception {
        assertEquals("HSQL Database Engine Driver", emHsql.unwrap(SessionImpl.class).connection().getMetaData().getDriverName());
    }
}
