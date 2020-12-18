package com.moralok.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.StringValueResolver;

/**
 * @author moralok
 * @since 2020/12/18 5:34 下午
 */
public class AwareBean implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {

    private ApplicationContext applicationContext;

    @Override
    public void setBeanName(String name) {
        System.out.println("AwareBean 的 BeanName " + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("AwareBean 的 ApplicationContext " + applicationContext);
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        String s = resolver.resolveStringValue("AwareBean 所在系统 ${os.name}，计算20-2=#{20-2}");
        System.out.println(s);
    }
}
