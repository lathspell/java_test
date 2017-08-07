package de.lathspell.test.config;

import javax.persistence.EntityManagerFactory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "de.lathspell.test.jpa")
@Slf4j
public class SpringTxConfiguration {

    /**
     * Creates the Spring component that handles the Spring @Transactional annotations.
     */
    @Bean
    public PlatformTransactionManager transactionManager(@Autowired EntityManagerFactory emf) {
        log.info("Using EMF: {} with Props: {}", emf, emf.getProperties());
        return new JpaTransactionManager(emf);
    }

}
