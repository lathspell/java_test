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
@Profile("h2")
@PropertySource("classpath:/db/h2.properties")
public class DataSourceH2Configuration {

    @Value("${driverClassName}")
    private String driverClassName;

    @Value("${url}")
    private String url;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Bean(name = "hibernateJpaProperties")
    public Properties hibernateJpaProperties() {
        Properties hibernateProps = new Properties();
        hibernateProps.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        hibernateProps.put("hibernate.hbm2ddl.auto", "create-drop"); // careful!
        hibernateProps.put("hibernate.format_sql", true);
        hibernateProps.put("hibernate.use_sql_comments", true);
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
        HikariConfig cfg = new HikariConfig();
        // JDBC Connection
        cfg.setDriverClassName(driverClassName);
        cfg.setJdbcUrl(url);
        cfg.setUsername(username);
        cfg.setPassword(password);
        // Connection Pool
        cfg.setMaximumPoolSize(5);
        cfg.setConnectionTestQuery("SELECT 1");
        cfg.setPoolName("SpringHikariCP");
        cfg.addDataSourceProperty("dataSource.cachePrepStmts", "true");
        cfg.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
        cfg.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
        cfg.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
        return new HikariDataSource(cfg);
    }
}
