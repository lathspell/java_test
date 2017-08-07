package de.lathspell.test.model.scopedproxy;

import lombok.extern.slf4j.Slf4j;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;

@Configuration
@Slf4j
public class ManagerConfiguration {

    @Bean
    @Scope(scopeName = SCOPE_PROTOTYPE, proxyMode = INTERFACES)
    public UserSettings userSettingsFactory() {
        log.info("Creating UserSettings");
        return new UserSettingsImpl();
    }
}
