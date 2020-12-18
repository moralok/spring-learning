package com.moralok.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author moralok
 * @since 2020/12/18 11:31 上午
 */
public class Dog implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public Dog() {
        System.out.println("Dog constructor……");
    }

    @PostConstruct
    public void init() {
        System.out.println("Dog init……");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Dog destroy……");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
