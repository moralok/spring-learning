package com.moralok.config.support.condition;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 判断是否为Mac系统
 *
 * @author moralok
 * @since 2020/12/17 6:08 下午
 */
public class MacCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        BeanDefinitionRegistry registry = context.getRegistry();
        System.out.println("MacCondition是否包含Bean bill定义" + registry.containsBeanDefinition("bill"));
        System.out.println("MacCondition是否包含Bean linus定义" + registry.containsBeanDefinition("linus"));
        System.out.println("MacCondition是否包含Bean jobs定义" + registry.containsBeanDefinition("jobs"));
        Environment environment = context.getEnvironment();
        String property = environment.getProperty("os.name");
        return property.contains("Mac");
    }
}
