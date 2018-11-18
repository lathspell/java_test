package test6;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * No XML is used, not even persistence.xml.
 */
@Configuration
@ComponentScan(basePackages = "test6")
@EnableJpaRepositories(basePackages = "test6")
@EnableTransactionManagement
public class Test6Config {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        // Those are Hibernate specific, not JPA 2.1!
        // adapter.setGenerateDdl(true);
        // adapter.setShowSql(true);
        return adapter;
    }

    @Bean
    public Properties jpaProperties() {
        Properties jpaProps = new Properties();
        jpaProps.setProperty("javax.persistence.schema-generation.database.action", "drop-and-create");
        jpaProps.setProperty("hibernate.format_sql", "true");
        jpaProps.setProperty("hibernate.use_sql_comments", "true");
        return jpaProps;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource ds, JpaVendorAdapter jpaVendorAdapter, Properties jpaProperties) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(ds);
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter); // Else "No PersistenceProvider specified in EntityManagerFactory configuration, ..."
        factoryBean.setJpaProperties(jpaProperties);
        factoryBean.setPackagesToScan("test6");
        factoryBean.afterPropertiesSet(); // As we factory this bean, Spring can't call @PostConstruct or similar
        return factoryBean.getNativeEntityManagerFactory();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}
