package de.lathspell.examples.server.logging;


/*
 * ----------------------------------------------------
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2014 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Universal logging filter.
 *
 * Can be used on client or server side. Has the highest priority.
 *
 * Uses Regex to check if the HTTP body should be logged as well or
 * rather not because it might be binary.
 *
 * @author Pavel Bucek (pavel.bucek at oracle.com)
 * @author Martin Matula (martin.matula at oracle.com)
 */
@PreMatching
@Priority(Integer.MIN_VALUE)
@SuppressWarnings("ClassWithMultipleLoggers")
public class Slf4jRestLoggingFilter implements ContainerRequestFilter, ClientRequestFilter, ContainerResponseFilter,
        ClientResponseFilter, WriterInterceptor {

    public static final String SUGGESTED_REGEX = "(text/.*|application/json|application/api-problem\\+json.*)";
    public static final String NONE_REGEX = "";
    public static final String ALL_REGEX = ".*";

    private static final Logger LOGGER = LoggerFactory.getLogger(Slf4jRestLoggingFilter.class);
    private static final String NOTIFICATION_PREFIX = "* ";
    private static final String REQUEST_PREFIX = "> ";
    private static final String RESPONSE_PREFIX = "< ";
    private static final String ENTITY_LOGGER_PROPERTY = Slf4jRestLoggingFilter.class.getName() + ".entityLogger";

    private static final Comparator<Map.Entry<String, List<String>>> COMPARATOR
            = new Comparator<Map.Entry<String, List<String>>>() {

        @Override
        public int compare(final Map.Entry<String, List<String>> o1, final Map.Entry<String, List<String>> o2) {
            return String.CASE_INSENSITIVE_ORDER.compare(o1.getKey(), o2.getKey());
        }
    };

    private static final int DEFAULT_MAX_ENTITY_SIZE = 8 * 1024;

    @SuppressWarnings("NonConstantLogger")
    private final Logger logger;
    private final AtomicLong _id = new AtomicLong(0);
    private final String isPrintableRegex;
    private final int maxEntitySize;

    /**
     * Create a logging filter logging the request and response to a default JDK
     * logger, named as the fully qualified class name of this class. Entity
     * logging is turned off by default.
     */
    public Slf4jRestLoggingFilter() {
        this(LOGGER, NONE_REGEX, DEFAULT_MAX_ENTITY_SIZE);
    }

    /**
     * Create a logging filter with custom SLF4J logger and suggested Content-Type Regex and maximum log entry size.
     *
     * @see Slf4jLoggingFilter#SUGGESTED_REGEX
     * @see Slf4jLoggingFilter#DEFAULT_MAX_ENTITY_SIZE
     * @param loggerName SLF4J Logger Name
     */
    public Slf4jRestLoggingFilter(String loggerName) {
        this(LoggerFactory.getLogger(loggerName), SUGGESTED_REGEX, DEFAULT_MAX_ENTITY_SIZE);
    }

    /**
     * Create a logging filter with custom logger and custom settings of entity logging.
     *
     * @param logger      the logger to log requests and responses.
     * @param printEntity if true, entity will be logged as well up to the default maxEntitySize, which is 8KB
     */
    @SuppressWarnings("BooleanParameter")
    public Slf4jRestLoggingFilter(final Logger logger, final boolean printEntity) {
        this(logger, printEntity ? ALL_REGEX : NONE_REGEX, DEFAULT_MAX_ENTITY_SIZE);
    }

    /**
     * Creates a logging filter with custom logger and entity logging turned on, but potentially limiting the size
     * of entity to be buffered and logged.
     *
     * @param logger        the logger to log requests and responses.
     * @param maxEntitySize maximum number of entity bytes to be logged (and buffered) - if the entity is larger,
     *                      logging filter will print (and buffer in memory) only the specified number of bytes
     *                      and print "...more..." string at the end.
     */
    public Slf4jRestLoggingFilter(final Logger logger, final int maxEntitySize) {
        this(logger, ALL_REGEX, maxEntitySize);
    }

    /**
     * Create a logging filter with custom logger and custom settings of entity logging.
     *
     * @param logger           the logger to log requests and responses.
     * @param isPrintableRegex ".*" oder "(text/.*|application/json)" to log entity with maxEntitiySize (default 8KB)
     * @param maxEntitySize    maximum number of entity bytes to be logged (and buffered) - if the entity is larger,
     *                         logging filter will print (and buffer in memory) only the specified number of bytes
     *                         and print "...more..." string at the end.
     */
    public Slf4jRestLoggingFilter(final Logger logger, final String isPrintableRegex, final int maxEntitySize) {
        logger.info("Initialisiere SLF4J REST LoggingFilter");
        this.logger = logger;
        this.isPrintableRegex = isPrintableRegex;
        this.maxEntitySize = maxEntitySize;
    }

    private void log(final StringBuilder b) {
        if (logger != null) {
            logger.debug(b.toString()); // #42#NetCologne# wir wollen debug; optional: .replaceAll("[^\\P{C}\\n]", "?")
        }
    }

    private StringBuilder prefixId(final StringBuilder b, final long id) {
        b.append(Long.toString(id)).append(" ");
        return b;
    }

    private void printRequestLine(final StringBuilder b, final String note, final long id, final String method, final URI uri) {
        prefixId(b, id).append(NOTIFICATION_PREFIX)
                .append(note)
                .append(" on thread ").append(Thread.currentThread().getName())
                .append("\n");
        prefixId(b, id).append(REQUEST_PREFIX).append(method).append(" ").
                append(uri.toASCIIString()).append("\n");
    }

    private void printResponseLine(final StringBuilder b, final String note, final long id, final int status) {
        prefixId(b, id).append(NOTIFICATION_PREFIX)
                .append(note)
                .append(" on thread ").append(Thread.currentThread().getName()).append("\n");
        prefixId(b, id).append(RESPONSE_PREFIX).
                append(Integer.toString(status)).
                append("\n");
    }

    private void printPrefixedHeaders(final StringBuilder b, final long id, final String prefix, final MultivaluedMap<String, String> headers) {
        for (final Map.Entry<String, List<String>> headerEntry : getSortedHeaders(headers.entrySet())) {
            final List<?> val = headerEntry.getValue();
            final String header = headerEntry.getKey();

            if (val.size() == 1) {
                prefixId(b, id).append(prefix).append(header).append(": ").append(val.get(0)).append("\n");
            } else {
                final StringBuilder sb = new StringBuilder();
                boolean add = false;
                for (final Object s : val) {
                    if (add) {
                        sb.append(',');
                    }
                    add = true;
                    sb.append(s);
                }
                prefixId(b, id).append(prefix).append(header).append(": ").append(sb.toString()).append("\n");
            }
        }
    }

    private Set<Map.Entry<String, List<String>>> getSortedHeaders(final Set<Map.Entry<String, List<String>>> headers) {
        final TreeSet<Map.Entry<String, List<String>>> sortedHeaders = new TreeSet<>(COMPARATOR);
        sortedHeaders.addAll(headers);
        return sortedHeaders;
    }

    private InputStream logInboundEntity(final StringBuilder b, InputStream stream) throws IOException {
        if (!stream.markSupported()) {
            stream = new BufferedInputStream(stream);
        }
        stream.mark(maxEntitySize + 1);
        final byte[] entity = new byte[maxEntitySize + 1];
        final int entitySize = stream.read(entity);
        b.append(new String(entity, 0, Math.min(entitySize, maxEntitySize)));
        if (entitySize > maxEntitySize) {
            b.append("...more...");
        }
        b.append('\n');
        stream.reset();
        return stream;
    }

    private boolean isRequestPrintable(final ClientRequestContext requestContext) {
        final String contentType = requestContext.getHeaderString("Content-Type");
        return (contentType == null ? "" : contentType).matches(isPrintableRegex);
    }

    private boolean isResponsePrintable(final ClientResponseContext responseContext) {
        final String contentType = responseContext.getHeaderString("Content-Type");
        return (contentType == null ? "" : contentType).matches(isPrintableRegex);
    }

    private boolean isRequestPrintable(final ContainerRequestContext requestContext) {
        final String contentType = requestContext.getHeaderString("Content-Type");
        return (contentType == null ? "" : contentType).matches(isPrintableRegex);
    }

    private boolean isResponsePrintable(final ContainerResponseContext responseContext) {
        final String contentType = responseContext.getHeaderString("Content-Type");
        return (contentType == null ? "" : contentType).matches(isPrintableRegex);
    }

    @Override
    public void filter(final ClientRequestContext context) throws IOException {
        final long id = this._id.incrementAndGet();
        final StringBuilder b = new StringBuilder();

        printRequestLine(b, "Sending client request", id, context.getMethod(), context.getUri());
        printPrefixedHeaders(b, id, REQUEST_PREFIX, context.getStringHeaders());

        if (context.hasEntity() && isRequestPrintable(context)) {
            final OutputStream stream = new LoggingStream(b, context.getEntityStream());
            context.setEntityStream(stream);
            context.setProperty(ENTITY_LOGGER_PROPERTY, stream);
            // not calling log(b) here - it will be called by the interceptor
        } else {
            log(b);
        }
    }

    @Override
    public void filter(final ClientRequestContext requestContext, final ClientResponseContext responseContext) throws IOException {
        final long id = this._id.incrementAndGet();
        final StringBuilder b = new StringBuilder();

        printResponseLine(b, "Client response received", id, responseContext.getStatus());
        printPrefixedHeaders(b, id, RESPONSE_PREFIX, responseContext.getHeaders());

        if (responseContext.hasEntity() && isResponsePrintable(responseContext)) {
            responseContext.setEntityStream(logInboundEntity(b, responseContext.getEntityStream()));
        }

        log(b);
    }

    @Override
    public void filter(final ContainerRequestContext context) throws IOException {
        final long id = this._id.incrementAndGet();
        final StringBuilder b = new StringBuilder();

        printRequestLine(b, "Server has received a request", id, context.getMethod(), context.getUriInfo().getRequestUri());
        printPrefixedHeaders(b, id, REQUEST_PREFIX, context.getHeaders());

        if (context.hasEntity() && isRequestPrintable(context)) {
            context.setEntityStream(logInboundEntity(b, context.getEntityStream()));
        }

        log(b);
    }

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) throws IOException {
        final long id = this._id.incrementAndGet();
        final StringBuilder b = new StringBuilder();

        printResponseLine(b, "Server responded with a response", id, responseContext.getStatus());
        printPrefixedHeaders(b, id, RESPONSE_PREFIX, responseContext.getStringHeaders());

        if (responseContext.hasEntity() && isResponsePrintable(responseContext)) {
            final OutputStream stream = new LoggingStream(b, responseContext.getEntityStream());
            responseContext.setEntityStream(stream);
            requestContext.setProperty(ENTITY_LOGGER_PROPERTY, stream);
            // not calling log(b) here - it will be called by the interceptor
        } else {
            log(b);
        }
    }

    @Override
    public void aroundWriteTo(final WriterInterceptorContext writerInterceptorContext) throws IOException, WebApplicationException {
        final LoggingStream stream = (LoggingStream) writerInterceptorContext.getProperty(ENTITY_LOGGER_PROPERTY);
        writerInterceptorContext.proceed();
        if (stream != null) {
            log(stream.getStringBuilder());
        }
    }

    private class LoggingStream extends OutputStream {

        private final StringBuilder b;
        private final OutputStream inner;
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        LoggingStream(final StringBuilder b, final OutputStream inner) {
            this.b = b;
            this.inner = inner;
        }

        StringBuilder getStringBuilder() {
            // write entity to the builder
            final byte[] entity = baos.toByteArray();

            b.append(new String(entity, 0, Math.min(entity.length, maxEntitySize)));
            if (entity.length > maxEntitySize) {
                b.append("...more...");
            }
            b.append('\n');

            return b;
        }

        @Override
        public void write(final int i) throws IOException {
            if (baos.size() <= maxEntitySize) {
                baos.write(i);
            }
            inner.write(i);
        }
    }
}
