package com.moralok.servlet.web;

import com.moralok.servlet.Dog;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author maowenrou
 * @since 2021/1/20 2:34 下午
 */
public class ListenerTest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println("test context attribute set by listener<br>");
        writer.println("<br>");
        // 获取ServletContext
        ServletContext servletContext = getServletContext();
        // 获取属性
        Dog dog = (Dog) servletContext.getAttribute("dog");
        writer.println("Dog's breed is: " + dog.getBreed());
    }
}
