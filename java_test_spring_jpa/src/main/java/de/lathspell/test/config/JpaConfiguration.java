package de.lathspell.test.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:/db.properties")
@EnableJpaRepositories(basePackages = "de.lathspell.test.jpa.repo2")
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
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }

    /**
     * Creates a transaction-type="RESOURCE_LOCAL" Entity Manager Factory.
     *
     * Could as well read a persistence.xml file.
     */
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPersistenceUnitName("userPU");
        factory.setPackagesToScan("de.lathspell.test.model");
        factory.setDataSource(dataSource());
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaProperties(hibernateProperties());
        factory.afterPropertiesSet();
        return factory.getNativeEntityManagerFactory();
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
