package com.moralok.ext;

import com.moralok.bean.Blue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 扩展原理
 *
 * @author moralok
 * @since 2020/12/23
 */
@Configuration
@ComponentScan("com.moralok.ext")
public class ExtConfig {

    @Bean
    public Blue blue() {
        return new Blue();
    }
}
