package com.moralok.config;

import com.moralok.aop.LogAspects;
import com.moralok.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 注解@EnableAspectJAutoProxy没有魔法！！！就是@Import导入了AspectJAutoProxyRegistrar
 * 其中注册或升级了internalAutoProxyCreator=AnnotationAwareAspectJAutoProxyCreator
 * 获取注解的属性，判断是否做某个操作
 *
 * 现在回过头再看就不会多想多迷糊，所有的@EnableXXX就是看在容器中注册了什么组件，组件什么时候工作，功能是什么
 *
 * AnnotationAwareAspectJAutoProxyCreator 注解感知的AspectJ自动代理创建器
 *     AspectJAwareAdvisorAutoProxyCreator AspectJ感知的通知自动代理创建器
 *         AbstractAdvisorAutoProxyCreator 抽象的通知自动代理创建器
 *             AbstractAutoProxyCreator 抽象的自动代理创建器
 *                 SmartInstantiationAwareBeanPostProcessor、BeanFactoryAware（关注后置处理器）
 *
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
