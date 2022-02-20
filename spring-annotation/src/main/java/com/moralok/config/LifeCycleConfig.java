package com.moralok.config;

import com.moralok.bean.Car;
import com.moralok.bean.Cat;
import com.moralok.bean.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author moralok
 * @since 2020/12/18 10:29 上午
 */
@Configuration
@ComponentScan(value = "com.moralok.component")
public class LifeCycleConfig {

    /**
     * 等同于配置文件中的init-method和destroy-method，必须没有参数，但是可以抛出任何异常
     * 初始化：对象创建完成，且属性赋值完毕后
     * 销毁：容器关闭的时候
     * 对于作用域为prototype时，获取时会执行初始化，但是容器不会继续管理Bean，也不会调用销毁方法
     *
     * @return
     */
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Car car() {
        return new Car();
    }

    /**
     * InitializingBean接口和DisposableBean接口
     * 由名称afterPropertiesSet可见时机
     *
     * @return
     */
    @Bean(initMethod = "init1", destroyMethod = "destroy1")
    public Cat cat() {
        return new Cat();
    }

    /**
     * JSR250 中的注解@PostConstruct的说明是在依赖注入（属性赋值）完成后，是包括@Value吗
     *
     * 关注：区别在哪里呢？先后顺序又是如何呢？了解实现原理两者皆解
     *
     * 为什么评论提到它可以在静态类中注入非静态类呢？感觉没什么优势啊？
     * 难道是指将准备注入的Bean注册到静态类的形式吗，其它两种不是也可以吗？静态类怎么保证属性已经被赋值呢，不是还要判断吗？
     * 还是说指将静态类交给容器管理，这很奇怪啊
     *
     * @return
     */
    @Bean
    public Dog dog() {
        return new Dog();
    }
}
