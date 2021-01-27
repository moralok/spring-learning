package com.moralok.servlet.filter;

import com.moralok.servlet.wrapper.CompressionResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * @author moralok
 * @since 2021/1/27 9:52 上午
 */
public class CompressionFilter implements Filter {

    private ServletContext ctx;

    private FilterConfig cfg;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.cfg = filterConfig;
        ctx = filterConfig.getServletContext();
        ctx.log(filterConfig.getFilterName() + " initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String header = httpServletRequest.getHeader("Accept-Encoding");
        if (header.contains("gzip")) {
            CompressionResponseWrapper compressionResponseWrapper = new CompressionResponseWrapper(httpServletResponse);
            compressionResponseWrapper.setHeader("Content-Encoding", "gzip");
            chain.doFilter(request, compressionResponseWrapper);
            GZIPOutputStream gzipOutputStream = compressionResponseWrapper.getGZIPOutputStream();
            gzipOutputStream.finish();
            ctx.log(cfg.getFilterName() + ": finished the request");
        } else {
            ctx.log(cfg.getFilterName() + ": no encoding performed.");
        }
    }

    @Override
    public void destroy() {
        cfg = null;
        ctx = null;
    }
}
