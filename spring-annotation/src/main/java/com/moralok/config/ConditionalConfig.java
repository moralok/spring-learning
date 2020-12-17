package com.moralok.config;

import com.moralok.bean.Person;
import com.moralok.config.support.condition.LinuxCondition;
import com.moralok.config.support.condition.MacCondition;
import com.moralok.config.support.condition.WindowsCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * 如果系统是windows，给容器中注册bill
 * 如果系统是linux，给容器中注册linus
 * @author moralok
 * @since 2020/12/17 5:57 下午
 */
@Configuration
public class ConditionalConfig {

    @Bean
    @Conditional(value = {WindowsCondition.class})
    public Person bill() {
        return new Person("bill", 60);
    }

    @Bean
    @Conditional(value = {LinuxCondition.class})
    public Person linus() {
        return new Person("linus", 50);
    }

    @Bean
    @Conditional(value = {MacCondition.class})
    public Person jobs() {
        return new Person("jobs", 40);
    }
}
