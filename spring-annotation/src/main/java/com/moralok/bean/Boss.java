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

    public Boss() {
        System.out.println("Boss 调用无参构造器……");
    }

    /**
     * 考虑使用构造器方法，在使用@Component注解时，标注在有参构造器上和参数上都是可以的
     * 甚至在只有一个有参构造器时，是可以省略的
     *
     * 看到吐槽深有同感：虽然很灵活，但是源码为了实现这个功能复杂得都不想看了吧。
     * 另外，这些细节记下来也不过是一个功能特性而已啊。
     * @param car
     */
    // @Autowired
    public Boss(Car car) {
        this.car = car;
        System.out.println("Boss 调用有参构造器……");
    }

    public Car getCar() {
        return car;
    }

    /**
     * 当@Autowired标注在方法上，Spring容器创建当前对象，就会调用方法，完成赋值
     * 方法使用的参数，自定义类型的值会从容器中获取
     *
     * 考虑普通的方法时，在使用@Component注解时，标注在方法上有效，参数上无效
     *
     * @param car
     */
    // @Autowired
    public void setCar(Car car) {
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
