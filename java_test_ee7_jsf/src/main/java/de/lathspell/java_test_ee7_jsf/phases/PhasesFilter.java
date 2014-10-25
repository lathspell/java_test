package de.lathspell.java_test_ee7_jsf.phases;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(urlPatterns = "/phases/*")
public class PhasesFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(PhasesFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("!!!! Filter init !!!!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("!!!! Servlet Filter !!!!");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.debug("!!!! Filter destroy !!!!");
    }

}
