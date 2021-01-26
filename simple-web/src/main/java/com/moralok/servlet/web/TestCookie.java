package com.moralok.servlet.web;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author maowenrou
 * @since 2021/1/26 2:10 下午
 */
public class TestCookie extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie = new Cookie("age", "30");
        cookie.setMaxAge(30);
        resp.addCookie(cookie);
        PrintWriter writer = resp.getWriter();
        writer.println("success");
    }
}
