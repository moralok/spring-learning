package com.moralok.config.support;

import com.moralok.bean.Rainbow;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author moralok
 * @since 2020/12/17
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * AnnotationMetadata：当前类的注解信息
     * BeanDefinitionRegistry：BeanDefinition注册类
     *     把需要添加到容器的Bean通过BeanDefinitionRegistry.registerBeanDefinition手动注册
     *
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean hasRed = registry.containsBeanDefinition("com.moralok.bean.Red");
        boolean hasBlue = registry.containsBeanDefinition("com.moralok.bean.Blue");
        if (hasRed && hasBlue) {
            // 允许自定义BeanDefinition
            BeanDefinition beanDefinition = new RootBeanDefinition(Rainbow.class);
            // 可以自定义Bean的id
            registry.registerBeanDefinition("rainbow", beanDefinition);
        }
    }
}
