package com.moralok.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;

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

    public void init1() throws Exception {
        System.out.println("Cat init1……");
    }

    /**
     * 这个注解是通过InitDestroyAnnotationBeanPostProcessor处理的，怎么保证它在其它的BeanPostProcessor之后呢
     * 或者说@Autowired的处理包含在属性赋值中吗
     *
     * @throws Exception
     */
    @PostConstruct
    public void init2() throws Exception {
        System.out.println("Cat init2……");
    }
}
