package de.lathspell.test;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "hsqlEMF", transactionManagerRef = "hsqlTM", basePackages = "de.lathspell.test.hsql")
@Slf4j
public class HsqlConfig {

    @Bean(name = "hsqlDSProps")
    //@ConfigurationProperties(prefix = "hsql.datasource")
    public DataSourceProperties dataSourceProperties() throws ClassNotFoundException {
        Class.forName(org.hsqldb.jdbc.JDBCDriver.class.getName());

        DataSourceProperties dsp = new DataSourceProperties();
        dsp.setUsername("sa");
        dsp.setPassword("sa");
        dsp.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        dsp.setUrl("jdbc:hsqldb:mem:testdb");
        return dsp;
    }

    @Bean(name = "hsqlDS")
    //@ConfigurationProperties(prefix = "hsql.datasource")
    public DataSource myhsqlDataSource(@Qualifier("hsqlDSProps") DataSourceProperties dataSourceProperties) {
        log.info("hsqlDS uses DSProps with Driver {}, {}, {}", dataSourceProperties.getDriverClassName(), dataSourceProperties.getUrl(), dataSourceProperties.getUsername());
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "hsqlJpaProps")
    public Properties hibernateJpaProperties() {
        Properties hibernateProps = new Properties();
        hibernateProps.put("hibernate.hbm2ddl.auto", "create-drop"); // careful!
        hibernateProps.put("hibernate.format_sql", false);
        hibernateProps.put("hibernate.use_sql_comments", true);
        hibernateProps.put("hibernate.show_sql", true);
        return hibernateProps;
    }

    @Bean(name = "hsqlEMF")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("hsqlDS") DataSource dataSource, @Qualifier("hsqlJpaProps") Properties jpaProperties) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPersistenceUnitName("hsqlPU");
        factory.setPackagesToScan("de.lathspell.test.hsql");
        factory.setDataSource(dataSource);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaDialect(new HibernateJpaDialect());
        factory.setJpaProperties(jpaProperties);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean(name = "hsqlTM")
    public PlatformTransactionManager transactionManager(@Qualifier("hsqlEMF") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
