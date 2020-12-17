package com.moralok.config.support.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 判断是否为Linux系统
 *
 * @author moralok
 * @since 2020/12/17 6:07 下午
 */
public class LinuxCondition implements Condition {

    /**
     * ConditionContext：判断条件是否满足的上下文环境
     * AnnotatedTypeMetadata：注解信息
     *
     * @param context
     * @param metadata
     * @return
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 1. 能获取到ioc使用的BeanFactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        // 2. 能获取到类加载器
        ClassLoader classLoader = context.getClassLoader();
        // 3. 能获取到当前环境信息
        Environment environment = context.getEnvironment();
        // 4. 能获取到Bean定义的注册中心
        BeanDefinitionRegistry registry = context.getRegistry();
        System.out.println("是否包含Bean bill定义" + registry.containsBeanDefinition("bill"));
        System.out.println("是否包含Bean linus定义" + registry.containsBeanDefinition("linus"));
        System.out.println("是否包含Bean jobs定义" + registry.containsBeanDefinition("jobs"));
        // 5. 能获取到资源加载器
        ResourceLoader resourceLoader = context.getResourceLoader();
        String property = environment.getProperty("os.name");
        return property.contains("Linux");
    }
}
