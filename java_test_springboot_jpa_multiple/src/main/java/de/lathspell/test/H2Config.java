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
@EnableJpaRepositories(entityManagerFactoryRef = "h2EMF", transactionManagerRef = "h2TM", basePackages = "de.lathspell.test.h2")
@Slf4j
public class H2Config {

    @Bean(name = "h2DSProps")
    //    @ConfigurationProperties(prefix = "h2.datasource")
    public DataSourceProperties dataSourceProperties() throws ClassNotFoundException {
        Class.forName(org.h2.Driver.class.getName());

        DataSourceProperties dsp = new DataSourceProperties();
        dsp.setUsername("sa");
        dsp.setDriverClassName("org.h2.Driver");
        dsp.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        return dsp;
    }

    @Bean(name = "h2DS")
    //@ConfigurationProperties(prefix = "h2.datasource")
    public DataSource dataSource(@Qualifier("h2DSProps") DataSourceProperties dataSourceProperties) {
        log.info("h2DS uses DSProps with Driver {}, {}, {}", dataSourceProperties.getDriverClassName(), dataSourceProperties.getUrl(), dataSourceProperties.getUsername());
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "h2JpaProps")
    public Properties hibernateJpaProperties() {
        Properties hibernateProps = new Properties();
        hibernateProps.put("hibernate.hbm2ddl.auto", "create-drop"); // careful!
        hibernateProps.put("hibernate.format_sql", true);
        hibernateProps.put("hibernate.use_sql_comments", true);
        hibernateProps.put("hibernate.show_sql", false);
        return hibernateProps;
    }

    @Bean(name = "h2EMF")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("h2DS") DataSource dataSource, @Qualifier("h2JpaProps") Properties jpaProperties) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPersistenceUnitName("h2PU");
        factory.setPackagesToScan("de.lathspell.test.h2");
        factory.setDataSource(dataSource);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaDialect(new HibernateJpaDialect());
        factory.setJpaProperties(jpaProperties);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean(name = "h2TM")
    public PlatformTransactionManager transactionManager(@Qualifier("h2EMF") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
