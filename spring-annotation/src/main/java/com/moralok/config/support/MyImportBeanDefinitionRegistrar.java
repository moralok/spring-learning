package com.moralok.config.support;

import com.moralok.bean.Color;
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
     * BeanDefinitionRegistry：BeanDefinition注册类（被传入的，不用想太多）
     *     把需要添加到容器的Bean通过BeanDefinitionRegistry.registerBeanDefinition手动注册
     *
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 注意可感知的信息
        boolean hasRed = registry.containsBeanDefinition("com.moralok.bean.Red");
        boolean hasBlue = registry.containsBeanDefinition("com.moralok.bean.Blue");
        if (hasRed && hasBlue) {
            // 允许自定义BeanDefinition
            BeanDefinition beanDefinition = new RootBeanDefinition(Color.class);
            // 可以自定义Bean的id
            registry.registerBeanDefinition("color", beanDefinition);
        }
    }
}
