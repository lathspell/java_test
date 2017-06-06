package de.lathspell.test.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class JpaConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("hibernateProperties")
    private Properties hibernateProperties;

    /**
     * Creates a transaction-type="RESOURCE_LOCAL" JPA Entity Manager Factory.
     *
     * Could as well read a persistence.xml file.
     */
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPersistenceUnitName("localUserPU");
        factory.setPackagesToScan("de.lathspell.test.model");
        factory.setDataSource(dataSource);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaDialect(new HibernateJpaDialect());
        factory.setJpaProperties(hibernateProperties);
        factory.afterPropertiesSet();
        return factory.getNativeEntityManagerFactory();
    }

}
