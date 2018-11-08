package de.lathspell.test;

import com.zaxxer.hikari.HikariDataSource;
import de.lathspell.test.myh2.Team;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "myh2EntityManagerFactory",
        transactionManagerRef = "myh2TransactionManager",
        basePackageClasses = Team.class)
public class MyH2Config {

    @Bean(name = "myh2DataSourceProperties")
    @ConfigurationProperties(prefix = "myh2.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "myh2DataSource")
    @ConfigurationProperties(prefix = "myh2.datasource")
    public DataSource dataSource(@Qualifier("dataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "myh2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("myh2DataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages(Team.class)
                .persistenceUnit("myh2PU")
                .build();
    }

    @Bean(name = "myh2TransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("myh2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
