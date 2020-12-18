package com.moralok.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author moralok
 * @since 2020/12/18 11:03 上午
 */
public class Cat implements InitializingBean, DisposableBean {

    public Cat() {
        System.out.println("Cat constructor……");
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("Cat destroy……");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Cat afterPropertiesSet……");
    }
}
