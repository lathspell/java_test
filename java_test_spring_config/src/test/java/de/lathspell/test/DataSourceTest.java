package de.lathspell.test;

import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/datasource.xml")
public class DataSourceTest {

    /* Autowired looks for matching property names to distinguish multiple beans of the same type. */
    
    @Autowired
    private DataSource ds1;

    @Autowired
    private DataSource ds2;

    @Test
    public void testDataSource1() throws Exception {
        assertThat(ds1, instanceOf(DataSource.class));
        assertThat(ds1.getConnection().getMetaData().getURL(), is("jdbc:hsqldb:mem:testdb1"));
    }

    @Test
    public void testDataSource2() throws Exception {
        assertThat(ds2, instanceOf(DataSource.class));
        assertThat(ds2.getConnection().getMetaData().getURL(), is("jdbc:hsqldb:mem:testdb2"));
    }
}
