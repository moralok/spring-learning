package com.moralok.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author moralok
 * @since 2020/12/18 4:58 下午
 */
@Component
public class Boss {

    private Car car;

    @Autowired
    public Boss(Car car) {
        this.car = car;
        System.out.println("Boss 调用有参构造器……");
    }

    public Car getCar() {
        return car;
    }

    /**
     * 当 @Autowired标注在方法上，Spring容器创建当前对象，就会调用方法，完成赋值
     * 方法使用的参数，自定义类型的值会从容器中获取
     *
     * @param car
     */
    public void setCar(@Autowired Car car) {
        System.out.println("Boss 调用 setCar……");
        this.car = car;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "car=" + car +
                '}';
    }
}
