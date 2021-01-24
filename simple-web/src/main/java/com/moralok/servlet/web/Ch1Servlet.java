package com.moralok.servlet.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @author moralok
 * @since 2021/1/23
 */
public class Ch1Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        Date today = new Date();
        writer.println("<html>" +
                "<body>" +
                "<h1>Chapter1 Servlet</h1>" +
                "<br>" +
                today +
                "</body>" +
                "</html>");
    }
}
