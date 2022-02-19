package com.moralok;

import com.moralok.config.LifeCycleConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author moralok
 * @since 2020/12/18 10:30 上午
 */
public class LifeCycleTest {

    /**
     * 注解@PostConstruct早于InitializingBean早于initMethod
     * 如果是同方法，注解@PostConstruct会覆盖掉initMethod的效果
     * 不同方法三者还能共存也是醉了啊，只能执行一次，估计有标记
     *
     * populateBean（为Bean属性赋值）
     * initializeBean
     *      applyBeanPostProcessorsBeforeInitialization
     *      invokeInitMethods
     *      applyBeanPostProcessorsAfterInitialization
     */
    @Test
    public void beanTest() {
        // 1、创建容器
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        System.out.println("容器创建完成");
        // 2、关闭容器
        ac.close();
    }
}
