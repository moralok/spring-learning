package com.moralok;

import com.moralok.bean.CircularA;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CircularReferenceTest {

    @Test
    public void testRegular() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("circular-reference-test.xml");
        CircularA circularA = (CircularA) applicationContext.getBean("circularA");
    }

    @Test
    public void testProxy() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("proxy-circular-reference-test.xml");
        CircularA circularA = (CircularA) applicationContext.getBean("proxyCircularA");
    }
}
