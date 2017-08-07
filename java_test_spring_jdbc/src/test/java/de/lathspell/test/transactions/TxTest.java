package de.lathspell.test.transactions;

import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import de.lathspell.test.config.AppConfiguration;
import de.lathspell.test.service.KvTxService;

@RunWith(SpringRunner.class)
@ActiveProfiles("p3")
@ContextConfiguration(classes = AppConfiguration.class)
@Slf4j
public class TxTest {

    @Autowired
    private KvTxService service;

    @Test
    public void testNamedParameters() {
        service.deleteByK("Aaron");
        assertThat(service.findVByK("Aaron"), is(nullValue()));
        service.createKv("Aaron", "Ari");
        assertThat(service.findVByK("Aaron"), is("Ari"));
    }

    /** Test that uses a @Sql to load a special test dataset. */
    @Test
    @Sql(scripts = "classpath:db/tests/tx-test-benny.sql")
    public void testBenny() {
        assertThat(service.findVByK("Aaron"), is(nullValue()));
        assertThat(service.findVByK("Benny"), is("Bonney"));
    }
}
