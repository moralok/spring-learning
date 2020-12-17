package com.moralok.config;

import com.moralok.config.support.ColorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author moralok
 * @since 2020/12/17
 */
@Configuration
public class FactoryBeanConfig {

    /**
     * 看起来注册了ColorFactoryBean，实际上注册了Color
     *
     * @return
     */
    @Bean
    public ColorFactoryBean colorFactoryBean() {
        return new ColorFactoryBean();
    }
}
