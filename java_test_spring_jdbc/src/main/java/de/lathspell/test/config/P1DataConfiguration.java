package de.lathspell.test.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Driver;

/**
 * Configuration that uses PropertySource.
 */
@Configuration
@Profile("p1")
@PropertySource("classpath:db/db.properties")
@Slf4j
public class P1DataConfiguration {

    @Value("${driverClassName}")
    private String driverClassName;

    @Value("${url}")
    private String url;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Value("classpath:db/schema.sql")
    private Resource schemaScript;

    @Value("classpath:db/test-data.sql")
    private Resource dataScript;

    @Bean
    public JdbcTemplate userJdbcTemplate() {
        log.debug("creating JdbcTemplate");
        return new JdbcTemplate(dataSource());
    }

    @Lazy
    @Bean
    public PlatformTransactionManager platformTransactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Lazy
    @Bean
    @SneakyThrows
    public DataSource dataSource() {
        log.debug("creating DataSource");
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        Class<? extends Driver> driver = (Class<? extends Driver>) Class.forName(driverClassName);
        dataSource.setDriverClass(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        // Either use the following or a DataSourceInitializer Bean
        // log.debug("executing DatabasePopulator");
        // DatabasePopulatorUtils.execute(databasePopulator(), dataSource);
        return dataSource;
    }

    /**
     * DataSource initializer if not DatabasePopulatorUtils is used in dataSource().
     */
    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        log.debug("creating DataSourceInitializer");
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        log.debug("creating DatabasePopulator");
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaScript);
        populator.addScript(dataScript);
        return populator;
    }

}
