package de.lathspell.stack1.utils;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates a SLF4J Logger for use with @Inject.
 *
 * LoggingInterceptor also uses this class but this producer works even if
 * the interceptor is not activated via beans.xml.
 */
public class LoggingProducer {

    @Produces
    public Logger produceLogger(InjectionPoint ip) {
        return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
    }
}
