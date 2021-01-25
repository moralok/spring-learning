package com.moralok.servlet.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author moralok
 * @since 2021/1/25 10:46 上午
 */
public class DownloadCsv extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/csv");
        ServletContext servletContext = req.getServletContext();
        InputStream is = servletContext.getResourceAsStream("/download.csv");
        int read = 0;
        byte[] bytes = new byte[1024];
        ServletOutputStream os = resp.getOutputStream();
        while ((read = is.read(bytes)) != -1) {
            os.write(bytes, 0, read);
        }
        os.flush();
        os.close();
    }
}
