package de.lathspell.test.filters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.StringEscapeUtils.escapeJava;

@WebFilter("/*")
public class LoggingFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            // Setup buffers
            BufferedRequestWrapper requestWrapper = new BufferedRequestWrapper(httpServletRequest);
            BufferedResponseWrapper responseWrapper = new BufferedResponseWrapper(httpServletResponse);

            // Log request
            String intro = httpServletRequest.getRemoteAddr() + " " + httpServletRequest.getMethod() + " " + httpServletRequest.getRequestURL();
            String qs = httpServletRequest.getQueryString();
            if (qs != null) {
                intro += "?" + qs;
            }
            String headers = "";
            for (Enumeration<String> ek = httpServletRequest.getHeaderNames(); ek.hasMoreElements();) {
                String k = ek.nextElement();
                for (Enumeration<String> ev = httpServletRequest.getHeaders(k); ev.hasMoreElements();) {
                    String v = ev.nextElement();
                    headers += k + ": " + v + "\n";
                }
            }
            String body = escapeJava(new String(requestWrapper.getBuffer())).replace("\\n", "\n");
            log.debug("REQUEST {}\n{}\n", intro, indent(headers + "\n" + body, "> "));

            // Continue processing this request
            chain.doFilter(requestWrapper, responseWrapper);

            // Log response
            intro = String.valueOf(httpServletResponse.getStatus());
            headers = "";
            for (String k : httpServletResponse.getHeaderNames()) {
                for (String v : httpServletResponse.getHeaders(k)) {
                    headers += k + ": " + v + "\n";
                }
            }
            body = escapeJava(new String(responseWrapper.getBuffer())).replace("\\n", "\n");
            log.debug("RESPONSE {}\n{}\n", intro, indent(headers + "\n" + body, "< "));

        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    /** Lazy variant of multi-line prefixing with trim. */
    String indent(String s, String prefix) {
        return prefix + s.replaceFirst("\\n$", "").replace("\n", "\n" + prefix);
    }
}
