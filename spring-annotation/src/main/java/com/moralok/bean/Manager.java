package com.moralok.bean;

/**
 * @author moralok
 * @since 2020/12/18 5:18 下午
 */
public class Manager {

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
