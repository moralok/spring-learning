package com.moralok;

import com.moralok.bean.Person;
import com.moralok.config.PropertyValueConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author moralok
 * @since 2020/12/18 2:39 下午
 */
public class PropertyValueTest {

    @Test
    public void test() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PropertyValueConfig.class);
        printBeanDefinitionNames(ac);

        Person person = (Person) ac.getBean("person");
        System.out.println(person);
        ac.close();
    }

    private void printBeanDefinitionNames(ApplicationContext ac) {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println("beanDefinitionName.........." + name);
        }
    }
}
