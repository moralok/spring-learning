package com.moralok.config;

import com.moralok.bean.Car;
import com.moralok.bean.Cat;
import com.moralok.bean.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author moralok
 * @since 2020/12/18 10:29 上午
 */
@Configuration
public class LifeCycleConfig {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Car car() {
        return new Car();
    }

    @Bean
    public Cat cat() {
        return new Cat();
    }

    @Bean
    public Dog dog() {
        return new Dog();
    }
}
