package com.moralok.servlet.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

/**
 * @author moralok
 * @since 2021/1/27 11:55 上午
 */
public class CompressionResponseWrapper extends HttpServletResponseWrapper {

    private GZIPServletOutputStream gzipServletOutputStream = null;

    private PrintWriter pw = null;

    private Object streamUsed;


    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response
     * @throws IllegalArgumentException if the response is null
     */
    public CompressionResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public GZIPOutputStream getGZIPOutputStream() {
        return this.gzipServletOutputStream.internalGzipOs;
    }

    @Override
    public void setContentLength(int len) {

    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (streamUsed != null && streamUsed != pw) {
            throw new IllegalStateException();
        }
        if (gzipServletOutputStream == null) {
            gzipServletOutputStream = new GZIPServletOutputStream(getResponse().getOutputStream());
            streamUsed = gzipServletOutputStream;
        }
        return gzipServletOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (streamUsed != null && streamUsed != gzipServletOutputStream) {
            throw new IllegalStateException();
        }
        if (pw == null) {
            gzipServletOutputStream = new GZIPServletOutputStream(getResponse().getOutputStream());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(gzipServletOutputStream, getResponse().getCharacterEncoding());
            pw = new PrintWriter(outputStreamWriter);
            streamUsed = pw;
        }
        return pw;
    }
}
