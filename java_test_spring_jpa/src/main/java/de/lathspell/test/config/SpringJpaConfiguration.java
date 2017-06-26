package de.lathspell.test.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class SpringJpaConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("hibernateJpaProperties")
    private Properties hibernateJpaProperties;


    /**
     * The name is important as this bean would not be found by its return type as an EntityManagerFactory will be quested.
     * 
     * @return An EntityManagerFactory subclass
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPersistenceUnitName("localUserPU");
        factory.setPackagesToScan("de.lathspell.test.model");
        factory.setDataSource(dataSource);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaDialect(new HibernateJpaDialect());
        factory.setJpaProperties(hibernateJpaProperties);
        factory.afterPropertiesSet();
        return factory;
    }

}
