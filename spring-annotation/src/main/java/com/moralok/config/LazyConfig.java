package com.moralok.config;

import com.moralok.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @author moralok
 * @since 2020/12/17 5:45 下午
 */
@Configuration
public class LazyConfig {

    @Bean
    @Lazy
    public Person zhangsan() {
        System.out.println("单实例默认容器启动的时候调用，懒加载将其延迟到第一次（获取）使用Bean");
        return new Person("zhangsan", 18);
    }
}
