package de.lathspell.test.config;

import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:/db.properties")
@EnableTransactionManagement
public class JpaConfiguration {

    @Value("${driverClassName}")
    private String driverClassName;

    @Value("${url}")
    private String url;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Bean
    public PlatformTransactionManager txManager() {
        return new HibernateTransactionManager(sessionFactory());
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new LocalSessionFactoryBuilder(dataSource())
                .scanPackages("de.lathspell.test.model")
                .addProperties(hibernateProperties())
                .buildSessionFactory();
    }

    @Bean
    public Properties hibernateProperties() {
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
