package com.moralok.component;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 要理解 Initializing 指的就是初始化方法，初始化的时机就是实例化和属性赋值后
 *
 * Spring底层常见 ApplicationContextAwareProcessor、BeanValidationPostProcessor、InitDestroyAnnotationBeanPostProcessor、AutowiredAnnotationBeanPostProcessor
 *
 * @author moralok
 * @since 2020/12/18 11:41 上午
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization……"+beanName+"=>"+bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization……"+beanName+"=>"+bean);
        return bean;
    }
}
