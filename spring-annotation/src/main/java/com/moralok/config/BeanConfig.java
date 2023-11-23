package com.moralok.config;

import com.moralok.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类=配置文件
 * 注解 @Configuration 告诉 Spring 这是一个配置类
 *
 * @author moralok
 * @since 2020/12/16 5:53 下午
 */
@Configuration
public class BeanConfig {

    /**
     * 给容器中注册一个Bean
     * 类型为返回值的类型
     * id默认是用方法名作为id，可以通过参数自定义id
     *
     * @return
     */
    @Bean
    public Person lisi() {
        return new Person("lisi", 20);
    }

    @Bean(value = "customName")
    public Person person() {
        lisi();
        return new Person("wangwu", 30);
    }
}
