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
     * BeanDefinitionRegistry：BeanDefinition注册类（被传入的，不用想太多）
     *     把需要添加到容器的Bean通过BeanDefinitionRegistry.registerBeanDefinition手动注册
     *
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 如果时候来注册的怎么办呢，感觉还是需要关注和把控顺序呀
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
