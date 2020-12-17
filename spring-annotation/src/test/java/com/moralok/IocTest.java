package com.moralok;

import com.moralok.bean.Person;
import com.moralok.config.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

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
        ApplicationContext ac = new AnnotationConfigApplicationContext(BeanConfig.class);
        Person lisi = (Person) ac.getBean("lisi");
        System.out.println(lisi);

        String[] beanNamesForType = ac.getBeanNamesForType(Person.class);
        for (String name : beanNamesForType) {
            System.out.println(name);
        }
    }

    @Test
    public void componentScanConfigTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentScanConfig.class);
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }

    @Test
    public void componentScanCustomTypeFilterTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentScanCustomTypeFilterConfig.class);
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println("beanDefinitionName.........." + name);
        }
    }

    @Test
    public void scopeTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ScopeConfig.class);
        System.out.println("容器创建完成");
        Object zhangsan1 = ac.getBean("zhangsan");
        Object zhangsan2 = ac.getBean("zhangsan");
        System.out.println("单实例Bean重复获取相等吗？"+(zhangsan1 == zhangsan2));
        Assert.assertEquals(zhangsan1, zhangsan2);
        Object lisi1 = ac.getBean("lisi");
        Object lisi2 = ac.getBean("lisi");
        System.out.println("多实例Bean重复获取相等吗？"+(lisi1 == lisi2));
        Assert.assertNotEquals(lisi1, lisi2);
    }

    @Test
    public void lazyTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(LazyConfig.class);
        System.out.println("容器创建完成");
        ac.getBean("zhangsan");
    }

    @Test
    public void conditionalTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ConditionalConfig.class);
        Environment environment = ac.getEnvironment();
        // 动态获取环境变量的值
        String property = environment.getProperty("os.name");
        System.out.println("os.name..."+property);
        String[] names = ac.getBeanNamesForType(Person.class);
        for (String name : names) {
            System.out.println("Name.........." + name);
        }
        Map<String, Person> beansOfType = ac.getBeansOfType(Person.class);
        System.out.println(beansOfType);
    }
}
