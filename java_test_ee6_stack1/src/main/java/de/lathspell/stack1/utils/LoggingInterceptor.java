package de.lathspell.stack1.utils;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This interceptor has to be activated using the beans.xml file.
 *
 * <pre>
 * <interceptors>
 *   <class>de.lathspell.stack1.utils.LoggingInterceptor</class>
 * </interceptors>
 * </pre>
 */
@Interceptor
@Loggable
public class LoggingInterceptor {

    @Context
    private HttpServletRequest request;

    @AroundInvoke
    public Object logMethod(InvocationContext ic) throws Exception {
        Logger log = LoggerFactory.getLogger(ic.getMethod().getDeclaringClass());
        if (request != null) {
            String qp = request.getQueryString() == null ? "" : ("?" + request.getQueryString());
            log.debug("{} {} {}{}", request.getRemoteAddr(), request.getMethod(), request.getRequestURL(), qp);
        }
        log.debug("entering {}", ic.getMethod().getName());
        try {
            return ic.proceed();
        } finally {
            log.debug("exiting {}", ic.getMethod().getName());
        }
    }
}
