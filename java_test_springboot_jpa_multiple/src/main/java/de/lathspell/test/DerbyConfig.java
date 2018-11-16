package de.lathspell.test;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "derbyEMF", transactionManagerRef = "derbyTM", basePackages = "de.lathspell.test.derby")
@Slf4j
public class DerbyConfig {

    @Primary
    @Bean(name = "derbyDSProps")
    //  @ConfigurationProperties(prefix = "derby.datasource")
    @SneakyThrows
    public DataSourceProperties dataSourceProperties() {
        Class.forName(org.apache.derby.jdbc.EmbeddedDriver.class.getName());

        DataSourceProperties dsp = new DataSourceProperties();
        dsp.setUsername("sa");
        dsp.setPassword("sa");
        dsp.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
        dsp.setUrl("jdbc:derby:memory:testdb;create=true");
        return dsp;
    }

    @Primary
    @Bean(name = "derbyDS")
    // @ConfigurationProperties(prefix = "derby.datasource")
    public DataSource dataSource(@Qualifier("derbyDSProps") DataSourceProperties dataSourceProperties) {
        log.info("derbyDS uses DSProps with Driver {}, {}, {}", dataSourceProperties.getDriverClassName(), dataSourceProperties.getUrl(), dataSourceProperties.getUsername());
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "derbyJpaProps")
    public Properties hibernateJpaProperties() {
        Properties hibernateProps = new Properties();
        hibernateProps.put("hibernate.hbm2ddl.auto", "create-drop"); // careful!
        hibernateProps.put("hibernate.format_sql", true);
        hibernateProps.put("hibernate.use_sql_comments", true);
        hibernateProps.put("hibernate.show_sql", false);
        return hibernateProps;
    }

    @Primary
    @Bean(name = "derbyEMF")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("derbyDS") DataSource dataSource, @Qualifier("derbyJpaProps") Properties jpaProperties) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPersistenceUnitName("derbyPU");
        factory.setPackagesToScan("de.lathspell.test.derby");
        factory.setDataSource(dataSource);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaDialect(new HibernateJpaDialect());
        factory.setJpaProperties(jpaProperties);
        factory.afterPropertiesSet();
        return factory;
    }

    @Primary
    @Bean(name = {"derbyTM"})
    public PlatformTransactionManager transactionManager(@Qualifier("derbyEMF") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
