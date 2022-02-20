package com.moralok.bean;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author moralok
 * @since 2020/12/18 5:18 下午
 */
public class Manager {

    /**
     * 配合@Bean可以不自己手动设置
     */
    // @Autowired
    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "car=" + car +
                '}';
    }
}
