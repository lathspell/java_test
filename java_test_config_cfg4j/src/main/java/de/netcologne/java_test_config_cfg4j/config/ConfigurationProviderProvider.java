package de.netcologne.java_test_config_cfg4j.config;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.classpath.ClasspathConfigurationSource;
import org.cfg4j.source.compose.MergeConfigurationSource;
import org.cfg4j.source.context.environment.Environment;
import org.cfg4j.source.context.environment.ImmutableEnvironment;
import org.cfg4j.source.files.FilesConfigurationSource;
import org.cfg4j.source.reload.ReloadStrategy;
import org.cfg4j.source.reload.strategy.PeriodicalReloadStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ConfigurationProviderProvider {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationProviderProvider.class);

    private ConfigurationProvider prov;

    @PostConstruct
    public void postConstruct() {
        log.info("");

        // Could be used if every config file would exist in several directories like
        // customer1/application.properties
        // customer2/application.properties
        // ...
        // Not so much usable to implement a kind of "cascade" or merged configuration.
        Environment environment = new ImmutableEnvironment("");

        ConfigurationSource classpathSource = new ClasspathConfigurationSource(() -> Arrays.asList(
                new File("application.properties").toPath(),
                new File("moduleFoo.yml").toPath()));

        // If existing, this file from /tmp is also parsed
        ConfigurationSource systemSource = new FilesConfigurationSource(() -> 
                Arrays.asList(new File("/tmp/moduleFoo.yml")).stream().filter((f) -> f.exists()).map((f) -> f.toPath()).collect(Collectors.toList()));

        ConfigurationSource mergedSource = new MergeConfigurationSource(classpathSource, systemSource);

        ReloadStrategy reloadStrategy = new PeriodicalReloadStrategy(5, TimeUnit.SECONDS);

        prov = new ConfigurationProviderBuilder()
                .withConfigurationSource(mergedSource)
                .withEnvironment(environment)
                .withReloadStrategy(reloadStrategy)
                .build();

        log.info("Built configuration provider " + prov);
        log.info("Dumping all loaded properties: " + prov.allConfigurationAsProperties());
    }

    @Produces
    public ConfigurationProvider getConfigurationProvider() {
        log.info("");
        return prov;
    }

}
