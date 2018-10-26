package test5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

// @Configuration and @Bean was introduced in Spring 3.0
@Configuration
public class DbConfig {

    @Bean
    public DataSource createDs() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScripts("common/schema.sql", "common/test-data.sql")
                .build();
    }

    @Bean
    public NamedParameterJdbcTemplate createNPJT(DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }
}
