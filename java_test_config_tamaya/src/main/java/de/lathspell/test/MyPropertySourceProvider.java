package de.lathspell.test;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.apache.tamaya.core.propertysource.SimplePropertySource;
import org.apache.tamaya.resource.AbstractPathPropertySourceProvider;
import org.apache.tamaya.spi.PropertySource;

/**
 * Provides PropertySource objects for each found *.properties file.
 * 
 * Register either using META-INF/services/org.apache.tamaya.spi.PropertySourceProvider or
 * via
 * <code>
 *  Collection<PropertySource> sources = new MyPropertySourceProvider().getPropertySources();
 *  ConfigurationContext ctx = ConfigurationProvider.getConfigurationContextBuilder().addPropertySources(sources).build();
 *  ConfigurationProvider.setConfigurationContext(ctx);
 * </code>
 * 
 */
public class MyPropertySourceProvider extends AbstractPathPropertySourceProvider {

    public MyPropertySourceProvider() {
        // .yml needs tamaya-formats!
        super("classpath:META-INF/*.properties", "classpath:META-INF/*.yml", "/tmp/*.yml"); // order is important!
    }

    @Override
    protected Collection<PropertySource> getPropertySources(URL url) {
        return Arrays.asList(new PropertySource[]{new SimplePropertySource(url)});
    }

}
