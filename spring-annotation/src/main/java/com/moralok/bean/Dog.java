package com.moralok.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author moralok
 * @since 2020/12/18 11:31 上午
 */
public class Dog {

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
}
