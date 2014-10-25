package de.lathspell.test.filters;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

/**
 * Wrapper around HttpServletRequestWrapper that buffers the received data.
 *
 * Can be used e.g. for logging servlet filters as the input data can normally
 * only read once by e.g. the REST servlet as the input stream cannot be reset.
 */
public class BufferedRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] buffer;

    public BufferedRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        buffer = IOUtils.toByteArray(request.getInputStream());
    }

    /** Returns the buffered data. */
    public byte[] getBuffer() {
        return buffer;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }
}
