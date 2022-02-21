package com.moralok;

import com.moralok.aop.MathCalculator;
import com.moralok.config.AopConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * registerBeanPostProcessors(beanFactory)注册BeanPostProcessor
 *     先获取ioc容器中已经定义的后置处理器
 *     加别的后置处理器
 *     按PriorityOrdered、Ordered和剩余的排序且按顺序注册（实际上就是创建实例）
 *         创建实例
 *         populateBean属性赋值
 *         初始化Bean initializeBean
 *             invokeAwareMethods
 *             applyBeanPostProcessorsBeforeInitialization
 *             invokeInitMethods
 *             applyBeanPostProcessorsAfterInitialization
 *         AnnotationAwareAspectJAutoProxyCreator创建成功，调用了initBeanFactory（里面包装了一下BeanFactory）
 *     BeanFactory.addBeanPostProcessor 后面Bean的创建就会被拦截了
 *
 * AnnotationAwareAspectJAutoProxyCreator是InstantiationAwareBeanPostProcessor类型
 *
 * @author moralok
 * @since 2020/12/19
 */
public class AopTest {

    /**
     * 将逻辑组件和切面类都加入Spring容器
     * 注解@Aspect标记切面类
     * 在通知方法上使用注解告诉Spring在何地（切入点表达式）、何时（@Before等注解）运行方法
     * 配置文件中使用标签 aop:aspectj-autoproxy 开启基于注解版的切面功能；等同于使用配置类上使用注解@EnableAspectJAutoProxy
     */
    @Test
    public void aopTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AopConfig.class);
        MathCalculator mathCalculator = ac.getBean(MathCalculator.class);
        mathCalculator.div(1, 1);
        // mathCalculator.div(1, 0);
        ac.close();
    }
}
