package com.moralok;

import com.moralok.bean.Blue;
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
     * Bean的定义是普通的（包含了属性），没有什么特别的。如果要被使用到，总是需要在某个地方进行配置，在这里的方式，就是明确地使用bean标签进行配置。
     * 配置文件的编写也是普通的，没有什么特别的。如果要被读取到，总是需要在某个地方进行配置，在这里的方式，就是明确地传入给了工厂（位置的格式暂不深究？）。
     * 高级的使用方式提供便捷的同时，也掩盖了一些简朴的道理。
     */
    @Test
    public void xmlConfigTest() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("pure-beans.xml");
        Person zhangsan = (Person) ac.getBean("zhangsan");
        System.out.println(zhangsan);
    }

    /**
     * 注解形式
     * BeanConfig配置类==配置文件（撇开具体细节，可以猜想：它们最终被解析成相同的元数据）
     * 看评论很多人在讨论两者的区别，就目前的形式上看，"无需配置"确实是一个令人困惑的描述。比较认可的有亮点有以下：
     * 1. 无需重新编译，就目前的使用经历，不太理解这点的好处，是以前很多没有源码吗？
     * 2. 类型安全
     * 好像是有那么点点便于理解？感觉xml配置入口更统一哇
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
    public void annotationConfigWithComponentTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(BeanConfigWithComponent.class);
        Person lisi1 = (Person) ac.getBean("lisi");
        Person lisi2 = (Person) ac.getBean("lisi");
        Person zhangsan = (Person) ac.getBean("zhangsan");
        System.out.println(lisi1 == lisi2);
    }

    /**
     * 包扫描
     * component-scan标签+@Controller、@Service、@Component等注解为什么属于配置文件的方式呢，感觉没使用过该方式的人有点困惑，明明用到了注解
     * 更精确地描述像是"包扫描的配置"是放在配置文件还是配置类中
     * 注解@Controller、@Service、@Component并不存在任何"魔法"，只是单纯的标签，需要通过配置才能"认识它们"
     *
     * 因为便捷的"魔法"，大家似乎不太关注被扫描的目标细节，似乎不出问题就OK
     * 注解@Configuration被扫描到会发生什么？
     */
    @Test
    public void componentScanConfigTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentScanConfig.class);
        printBeanDefinitionNames(ac);
    }

    /**
     * 自定义TypeFilter
     * 看起来，不论是annotation还是assignable type的方式，都是通过默认已有的TypeFilter实现的
     * 也就是再扫描过程中，肯定有一个通用的过滤机制介入扫描过程，可以留意
     *
     * 我人傻了，没注意返回了一个配置类，注入了一大堆，还以为失效了
     */
    @Test
    public void componentScanCustomTypeFilterTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentScanCustomTypeFilterConfig.class);
        printBeanDefinitionNames(ac);
    }

    /**
     * Bean 的作用域，然而没有遇到过使用非单实例的，应用场景这么稀少吗
     */
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

    /**
     * 面向单实例
     */
    @Test
    public void lazyTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(LazyConfig.class);
        System.out.println("容器创建完成");
        ac.getBean("zhangsan");
    }

    /**
     * 满足条件才注册，虽然能想到一些应用场景，但是合不合适用到生产环境，会有些犹豫呢
     * 可以再启动参数中指定
     *
     * 注解放在类（需要配置类）上，类中所有的才能生效
     */
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

    /**
     * SpringBoot底层使用较多，比如@EnableXXX之类。可替代吗？感觉不到一定要用的理由呢。
     */
    @Test
    public void importTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ImportConfig.class);
        printBeanDefinitionNames(ac);
        Blue blue = ac.getBean(Blue.class);
        System.out.println(blue);
    }

    @Test
    public void factoryBeanTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(FactoryBeanConfig.class);
        printBeanDefinitionNames(ac);
        Object colorFactoryBean1 = ac.getBean("colorFactoryBean");
        if (colorFactoryBean1 != null) {
            System.out.println(colorFactoryBean1.getClass());
        }
        Object colorFactoryBean2 = ac.getBean("colorFactoryBean");
        Assert.assertNotEquals(colorFactoryBean1, colorFactoryBean2);
        Object colorFactoryBeanItself = ac.getBean("&colorFactoryBean");
        if (colorFactoryBeanItself != null) {
            System.out.println(colorFactoryBeanItself.getClass());
        }

    }

    private void printBeanDefinitionNames(ApplicationContext ac) {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println("beanDefinitionName.........." + name);
        }
    }
}
