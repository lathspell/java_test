package de.lathspell.test.config;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Access to all configuration values in the "foo.*" namespace.
 *
 * Objects of this class are automatically populated from application.yml.
 *
 * Setter are needed, hence Lombok's @Data is used.
 *
 * JSR 303 Bean Validation is supported if an implementation is found on the classpath
 * (hence the dependency to hibernate-validate in this project).
 */
@Component
@ConfigurationProperties(prefix = "foo", ignoreUnknownFields = false, ignoreInvalidFields = false)
@Validated
@Data
@Slf4j
public class FooConfig {

    @Size(min = 2, max = 14)
    private String username;

    @NotNull
    private InetAddress remoteAddress;

    private Map<Integer, String> players;
    
    private List<String> countries;
}
