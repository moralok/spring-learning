package com.moralok.config;

import com.moralok.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfigWithComponent {

    @Bean
    public Person lisi() {
        return new Person("lisi", 20);
    }

    @Bean
    public Person zhangsan() {
        lisi();
        return new Person("zhangsan", 20);
    }
}
