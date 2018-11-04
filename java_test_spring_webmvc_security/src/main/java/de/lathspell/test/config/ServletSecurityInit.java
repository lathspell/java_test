package de.lathspell.test.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * This only adds the Spring Security filter chain.
 */
@Order(2)
public class ServletSecurityInit extends AbstractSecurityWebApplicationInitializer /* implements WebApplicationInitializer */ {

}
