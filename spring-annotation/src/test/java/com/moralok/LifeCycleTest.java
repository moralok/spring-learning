package com.moralok;

import com.moralok.config.LifeCycleConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author moralok
 * @since 2020/12/18 10:30 上午
 */
public class LifeCycleTest {

    @Test
    public void beanTest() {
        // 1、创建容器
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        System.out.println("容器创建完成");
        ac.close();
    }
}
