package com.moralok.bean;

import org.springframework.stereotype.Component;

/**
 * @author moralok
 * @since 2020/12/18 10:26 上午
 */
@Component
public class Car {

    public Car() {
        System.out.println("car constructor……");
    }

    public void init() {
        System.out.println("car init……");
    }

    public void destroy() {
        System.out.println("car destroy……");
    }
}
