package com.moralok;

import com.moralok.bean.Person;
import com.moralok.config.PersonConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author moralok
 * @since 2020/12/16 5:44 下午
 */
public class IocTest {

    /**
     * xml形式
     */
    @Test
    public void xmlConfigTest() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        Person zhangsan = (Person) ac.getBean("zhangsan");
        System.out.println(zhangsan);
    }

    /**
     * 注解形式
     */
    @Test
    public void annotationConfigTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(PersonConfig.class);
        Person lisi = (Person) ac.getBean("lisi");
        System.out.println(lisi);

        String[] beanNamesForType = ac.getBeanNamesForType(Person.class);
        for (String name : beanNamesForType) {
            System.out.println(name);
        }
    }
}
