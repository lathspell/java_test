package de.lathspell.test.config;

import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("postgres")
@PropertySource("classpath:/db/postgres.properties")
public class DataSourcePostgresConfiguration {

    @Value("${hostname}")
    private String hostname;

    @Value("${database}")
    private String database;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Bean(name = "hibernateProperties")
    public Properties hibernateProperties() {
        Properties hibernateProps = new Properties();
        hibernateProps.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProps.put("hibernate.hbm2ddl.auto", "create-drop"); // careful!
        hibernateProps.put("hibernate.format_sql", false);
        hibernateProps.put("hibernate.use_sql_comments", false);
        hibernateProps.put("hibernate.show_sql", false);
        return hibernateProps;
    }

    /**
     * Creates an Hikari Connection Pool based JDBC DataSource.
     *
     * TODO: See HikariConfigurationUtil
     */
    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        // JDBC Connection
        Properties props = new Properties();
        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
        props.setProperty("dataSource.serverName", hostname);
        props.setProperty("dataSource.user", username);
        props.setProperty("dataSource.password", password);
        props.setProperty("dataSource.databaseName", database);

        // Connection Pool
        HikariConfig cfg = new HikariConfig(props);
        cfg.setMaximumPoolSize(5);
        cfg.setConnectionTestQuery("SELECT 1");
        cfg.setPoolName("SpringHikariCP");

        return new HikariDataSource(cfg);
    }
}
