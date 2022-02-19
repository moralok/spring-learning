package com.moralok.config;

import com.moralok.bean.Person;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author moralok
 * @since 2020/12/17 5:22 下午
 */
@Configuration
public class ScopeConfig {

    @Bean
    public Person zhangsan() {
        System.out.println("容器启动的时候获取");
        return new Person("zhangsan", 18);
    }

    /**
     * org.springframework.beans.factory.config.ConfigurableBeanFactory#SCOPE_PROTOTYPE
     * org.springframework.beans.factory.config.ConfigurableBeanFactory#SCOPE_SINGLETON
     * org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST
     * org.springframework.web.context.WebApplicationContext#SCOPE_SESSION
     *
     * SCOPE_PROTOTYPE：多实例，每次获取的时候调用
     * SCOPE_SINGLETON：单实例，启用容器过程中就创建对象放入容器中（只调用一次）
     * 以下需要web环境
     * SCOPE_REQUEST：同一次请求创建一个实例
     * SCOPE_SESSION：同一个session创建一个实例
     *
     * @return
     */
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean
    public Person lisi() {
        System.out.println("使用到该组件的时候获取");
        return new Person("lisi", 20);
    }
}
