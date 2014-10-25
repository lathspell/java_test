package de.lathspell.test.webservices;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@WebFilter("/*")
public class MyRestLoggingFilter implements Filter {

    final static Logger log = LoggerFactory.getLogger(MyRestLoggingFilter.class);

    // BUG: Does not work yet in a Provider ContainerRequestFilter according to https://java.net/jira/browse/JERSEY-1960
    // @Context private HttpServletRequest request;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // This is a Servlet Filter, not something REST specific so the above
        // FilterConfig can only be set using <init-param> tags in the
        // web.xml servlet configuration, not from MyRestConfig.java.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        MDC.put("myClientId", request.getRemoteAddr());
        log.info("Received request from {} to {}", request.getRemoteAddr(), req.getRequestURI());
        chain.doFilter(request, response);
        log.info("Finished");
        MDC.remove("myClientId");
    }

    @Override
    public void destroy() {
        // nothing
    }
}
