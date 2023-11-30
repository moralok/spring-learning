package com.moralok.dubbo.spi.test;

import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

public class JavaSPITest {

    @Test
    void bark() {
        System.out.println("Java SPI");
        System.out.println("============");
        ServiceLoader<Animal> serviceLoader = ServiceLoader.load(Animal.class);
        serviceLoader.forEach(Animal::bark);
    }
}
