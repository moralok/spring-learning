package com.moralok.servlet.web;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * @author moralok
 * @since 2021/1/24
 */
public class HttpServletRequestTest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("RequestParams color: " + req.getParameter("color"));
        System.out.println("RequestParams colors: " + Arrays.toString(req.getParameterValues("colors")));
        System.out.println("User-Agent: " + req.getHeader("User-Agent"));
        for (Cookie c : req.getCookies()) {
            System.out.println("Cookies: " + c.getName() + ": " + c.getValue());
        }
        Enumeration<String> names = req.getSession().getAttributeNames();
        System.out.println("Session: ");
        while (names.hasMoreElements()) {
            System.out.println("Session: " + names.nextElement());
        }
        System.out.println("HTTP Method: " + req.getMethod());
        System.out.println("InputStream: " + req.getInputStream().toString());
    }
}
