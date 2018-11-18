package test12;

import common.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * An anonymous ResultSetExtractor is used to flatten all rows into one single integer.
 * It's the same as `SELECT sum(length(name)) FROM persons`.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Test12Config.class)
@Slf4j
public class ResultSetExtractorTest {
    @Autowired
    private JdbcTemplate template;

    @Autowired
    private PersonService svc;

    @Test
    public void test1() {
        Person p1 = new Person(null, "Tim");
        svc.save(p1);

        Person p2 = new Person(null, "Anna");
        svc.save(p2);

        int result = template.query("SELECT name FROM persons", new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                int sum = 0;
                while (rs.next()) {
                    sum += rs.getString("name").length();
                }
                return sum;
            }
        });
        assertEquals(7, result);
    }
}