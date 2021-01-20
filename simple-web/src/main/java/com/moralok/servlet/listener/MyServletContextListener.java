package com.moralok.servlet.listener;

import com.moralok.servlet.Dog;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 自定义上下文监听者
 * 可以实现创建需要全局共享的对象的功能，比如数据源
 *
 * @author moralok
 * @since 2021/1/20 2:22 下午
 */
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("MyServletContextListener contextInitialized receive servletContextEvent: " + servletContextEvent.toString());
        ServletContext servletContext = servletContextEvent.getServletContext();
        String breed = servletContext.getInitParameter("breed");
        Dog dog = new Dog(breed);
        servletContext.setAttribute("dog", dog);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
