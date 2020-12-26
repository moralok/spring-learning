package com.moralok;

import com.moralok.ext.ExtConfig;
import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author moralok
 * @since 2020/12/23
 */
public class ExtTest {

    @Test
    public void beanFactoryPostProcessorTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ExtConfig.class);
        ac.close();
    }

    @Test
    public void beanDefinitionRegistryPostProcessorTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ExtConfig.class);
        ac.publishEvent(new ApplicationEvent(new String("我发布的事件")) {
        });
        ac.close();
    }
}
