package com.moralok.servlet.web;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @author moralok
 * @since 2021/1/27 2:37 下午
 */
public class Ch13Compression extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // PrintWriter writer = resp.getWriter();
        // writer.println("Hello World");
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.println("Are you ok?");
    }
}
