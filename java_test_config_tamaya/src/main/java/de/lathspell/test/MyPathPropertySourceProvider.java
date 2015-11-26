package de.lathspell.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.apache.tamaya.core.propertysource.SimplePropertySource;
import org.apache.tamaya.spi.PropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.tamaya.spi.PropertySourceProvider;

public class MyPathPropertySourceProvider implements PropertySourceProvider {

    private static final Logger log = LoggerFactory.getLogger(MyPathPropertySourceProvider.class);

    public MyPathPropertySourceProvider() {
    }

    @Override
    public Collection<PropertySource> getPropertySources() {
        try {
            return Arrays.asList(new PropertySource[]{new SimplePropertySource(new URL("classpath:META-INF/*.properties"))});
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
