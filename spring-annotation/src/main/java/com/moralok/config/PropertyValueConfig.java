package com.moralok.config;

import com.moralok.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author moralok
 * @since 2020/12/18 2:37 下午
 */
@Configuration
public class PropertyValueConfig {

    @Bean
    public Person person() {
        return new Person();
    }
}
