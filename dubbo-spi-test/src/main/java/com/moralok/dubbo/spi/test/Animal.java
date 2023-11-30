package com.moralok.dubbo.spi.test;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface Animal {

    void bark();
}
