package de.lathspell.test;

import de.lathspell.test.myhsql.Address;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
/*
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "myhsqlEmf",
        transactionManagerRef = "myhsqlTransactionManager",
        basePackageClasses = Address.class)*/
public class MyHsqlConfig {
/*
    @Bean(name = "myhsqlDataSource")
    @ConfigurationProperties(prefix = "myhsql.datasource")
    public DataSource myhsqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "myhsqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("myhsqlDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages(Address.class)
                .persistenceUnit("myhsqlPU")
                .build();
    }

    @Bean(name = "myhsqlTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("myhsqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
    */
}
