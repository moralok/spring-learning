package com.moralok.config;

import com.moralok.aop.LogAspects;
import com.moralok.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author moralok
 * @since 2020/12/19
 */
@Configuration
@EnableAspectJAutoProxy
public class AopConfig {

    @Bean
    public MathCalculator mathCalculator() {
        return new MathCalculator();
    }

    @Bean
    public LogAspects logAspects() {
        return new LogAspects();
    }
}
