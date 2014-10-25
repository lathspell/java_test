package de.lathspell.test.filters;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

@WebFilter("/*")
public class EwuTreeFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(EwuTreeFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String uri = ((HttpServletRequest) request).getRequestURI();

        Matcher matcher = Pattern.compile("^/([^/]+)/(test|devel|beta|release)/(.*)$").matcher(uri);
        if (!matcher.matches()) {
            throw new ServerException("Ungültige URI: " + uri);
        }
        log.debug("EWU_TREE=" + matcher.group(2));        
        System.setProperty("EWU_TREE", matcher.group(2));
       // String newUri = "/" + matcher.group(3); // contextPath relative!
        // request.getRequestDispatcher(newUri).forward(request, response);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
