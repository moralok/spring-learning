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

    @Test
    public void aopTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AopConfig.class);
        MathCalculator mathCalculator = ac.getBean(MathCalculator.class);
        mathCalculator.div(1, 1);
        // mathCalculator.div(1, 0);
        ac.close();
    }
}
