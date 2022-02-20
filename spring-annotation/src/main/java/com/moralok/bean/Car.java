package com.moralok.bean;

import org.springframework.stereotype.Component;

/**
 * @author moralok
 * @since 2020/12/18 10:26 上午
 */
@Component
public class Car {

    private String name;

    public Car() {
        System.out.println("car constructor……");
        name = "originalName";
    }

    public void init() {
        System.out.println("car init……");
    }

    public void destroy() {
        System.out.println("car destroy……");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                '}';
    }
}
