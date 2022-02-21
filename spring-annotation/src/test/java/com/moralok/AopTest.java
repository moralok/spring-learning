package com.moralok;

import com.moralok.aop.MathCalculator;
import com.moralok.config.AopConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
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
