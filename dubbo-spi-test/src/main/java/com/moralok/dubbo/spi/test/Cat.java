package com.moralok.dubbo.spi.test;

public class Cat implements Animal {
    @Override
    public void bark() {
        System.out.println("Cat bark...");
    }
}
