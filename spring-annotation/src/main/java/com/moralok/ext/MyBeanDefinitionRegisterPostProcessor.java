package com.moralok.ext;

import com.moralok.bean.Blue;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * @author moralok
 * @since 2020/12/24
 */
@Component
public class MyBeanDefinitionRegisterPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // BeanDefinitionRegistry Bean 定义存储中心
        System.out.println("MyBeanDefinitionRegisterPostProcessor...postProcessBeanDefinitionRegistry Bean数量 " + registry.getBeanDefinitionCount());
        RootBeanDefinition anotherBlue = new RootBeanDefinition(Blue.class);
        registry.registerBeanDefinition("anotherBlue", anotherBlue);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanDefinitionRegisterPostProcessor...postProcessBeanFactory Bean数量 " + beanFactory.getBeanDefinitionCount());
    }
}
