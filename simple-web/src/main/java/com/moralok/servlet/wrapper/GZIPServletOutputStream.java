package com.moralok.servlet.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * @author moralok
 * @since 2021/1/27 1:22 下午
 */
public class GZIPServletOutputStream extends ServletOutputStream {

    GZIPOutputStream internalGzipOs;

    public GZIPServletOutputStream(ServletOutputStream sos) throws IOException {
        this.internalGzipOs = new GZIPOutputStream(sos);
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }

    @Override
    public void write(int b) throws IOException {
        System.out.println((char) b);
        internalGzipOs.write(b);
    }
}
