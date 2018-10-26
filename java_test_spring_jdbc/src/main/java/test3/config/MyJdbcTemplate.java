package test3.config;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class MyJdbcTemplate extends NamedParameterJdbcTemplate {

    public MyJdbcTemplate(DataSource ds) {
        super(ds);
    }
}