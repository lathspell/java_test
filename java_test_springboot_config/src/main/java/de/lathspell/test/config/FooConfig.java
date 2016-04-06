package de.lathspell.test.config;

import java.net.InetAddress;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Access to all configuration values in the "foo.*" namespace.
 * 
 * Objects of this class are automatically populated from application.yml.
 * 
 * Setter are needed, hence Lombok's @Data is used.
 * 
 * JSR 303 Bean Validation is supported if an implementation is found on the classpath!
 */
@Component
@ConfigurationProperties(prefix = "foo", ignoreUnknownFields = false, ignoreInvalidFields = false)
@Data
public class FooConfig {

    @Size(min = 2, max = 14)
    private String username;

    @NotNull
    private InetAddress remoteAddress;
}
