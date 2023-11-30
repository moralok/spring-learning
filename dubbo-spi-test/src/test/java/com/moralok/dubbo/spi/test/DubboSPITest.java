package com.moralok.dubbo.spi.test;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.jupiter.api.Test;

public class DubboSPITest {

    @Test
    void bark() {
        System.out.println("Dubbo SPI");
        System.out.println("============");
        ExtensionLoader<Animal> extensionLoader = ExtensionLoader.getExtensionLoader(Animal.class);
        Animal dog = extensionLoader.getExtension("dog");
        dog.bark();
        Animal cat = extensionLoader.getExtension("cat");
        cat.bark();
    }
}
