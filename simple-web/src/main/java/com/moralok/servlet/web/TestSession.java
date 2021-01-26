package com.moralok.servlet.web;

import com.moralok.servlet.Dog;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author moralok
 * @since 2021/1/26 1:47 下午
 */
public class TestSession extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.isNew()) {
            System.out.println("这是一个新的会话");
        } else {
            System.out.println("这是一个旧的会话");
        }

        String myName = (String) session.getAttribute("myName");
        System.out.println("myName: " + myName);
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now: " + now.toString());
        session.setAttribute("myName", now.toString());
        session.setAttribute("dog", new Dog("A Dog"));
        session.removeAttribute("dog");
    }
}
