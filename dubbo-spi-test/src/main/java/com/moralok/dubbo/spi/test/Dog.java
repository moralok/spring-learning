package com.moralok.dubbo.spi.test;

public class Dog implements Animal {
    @Override
    public void bark() {
        System.out.println("Dog bark...");
    }
}
