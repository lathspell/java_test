package de.lathspell.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.lathspell.test.model.ctorautowire.DbConfiguration;
import de.lathspell.test.model.ctorautowire.DbDAO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DbConfiguration.class})
public class JavaConfigCtorAutwireTest {

    @Autowired
    private DbDAO dbDAO;

    @Test
    public void test() {
        assertThat(dbDAO.getProps().getUrl(), is("foo://localhost/customers"));
    }
}
