package com.moralok.servlet;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * @author moralok
 * @since 2021/1/20 2:20 下午
 */
public class Dog implements HttpSessionBindingListener {

    private String breed;

    public Dog(String breed) {
        this.breed = breed;
        System.out.println("Dog constructed, breed is: " + breed);
    }

    public String getBreed() {
        return breed;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        System.out.println(getBreed() + " bound");
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        System.out.println(getBreed() + " unbound");

    }
}
