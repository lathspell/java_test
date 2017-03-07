package de.lathspell.test.model.ctorautowire;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = DbConfiguration.class)
public class DbConfiguration {

    @Bean
    @Qualifier("customers")
    public DbProperties dbPropsFactory() {
        return new DbProperties("foo://localhost/customers");
    }
}
