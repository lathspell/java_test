package de.lathspell.test.filters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Wrapper around HttpServletResponseWrapper that buffers the sent data.
 *
 * Can be used e.g. for logging servlet filters as the output data can normally
 * not be shown once sent.
 *
 * @see LoggingFilter
 */
public class BufferedResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream bufferOutputStream;

    private final ServletOutputStream servletOutputStream;

    public BufferedResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        bufferOutputStream = new ByteArrayOutputStream();
        servletOutputStream = response.getOutputStream();
    }

    /** Returns the buffered data that has also been sent to the servlet chain. */
    public byte[] getBuffer() {
        return bufferOutputStream.toByteArray();
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                bufferOutputStream.write(b);
                servletOutputStream.write(b);
            }
        };
    }
}
