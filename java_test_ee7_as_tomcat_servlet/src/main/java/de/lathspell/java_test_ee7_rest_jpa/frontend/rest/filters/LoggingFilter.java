package de.lathspell.java_test_ee7_rest_jpa.frontend.rest.filters;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import lombok.extern.slf4j.Slf4j;

@PreMatching
@Provider
@Slf4j
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Context
    private HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext rc) {
        log.info("REST Server Request: {} {} from {}", rc.getMethod(), rc.getUriInfo().getRequestUri(), request.getRemoteAddr());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        log.info("REST Server Response: {}", responseContext.getStatusInfo());
    }

}
