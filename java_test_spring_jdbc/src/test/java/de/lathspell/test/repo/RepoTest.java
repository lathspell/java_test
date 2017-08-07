package de.lathspell.test.repo;

import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import de.lathspell.test.config.AppConfiguration;

@RunWith(SpringRunner.class)
@ActiveProfiles("p3")
@ContextConfiguration(classes = AppConfiguration.class)
@Slf4j
public class RepoTest {

    @Autowired
    @Qualifier("kvTemplateRepo")
    private KvRepo kvRepo;

    @Test
    public void test1() {
        Kv result = kvRepo.findByKey("Tim");
        assertThat(result, is(new Kv("Tim", "Tayler")));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testNotFind() {
        kvRepo.findByKey("Does Not Exist");
    }

    @Test
    public void testLoggingRowCallback() {
        kvRepo.loggingFindByKey();
    }
}
