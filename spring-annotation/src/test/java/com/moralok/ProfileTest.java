package com.moralok;

import com.moralok.config.ProfileConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring为我们提供的可以根据当前环境，动态地切换一系列组建的功能
 * 为啥不用@Conditional呢？个人感觉可能这个更明确地表现依赖于环境？底层原理ProfileCondition
 * 配置文件的环境应该不能完全替代吧？
 *
 * 注解@Profile还可以配置在配置类上，整个配置类都不会加载，里面不管怎么写都没用了（配置文件有对应的处理方式吗？）
 * 没有标示的任何环境都会加载
 *
 * @author moralok
 * @since 2020/12/18 6:30 下午
 */
public class ProfileTest {

    @Test
    public void severalTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProfileConfig.class);
        printBeanDefinitionNames(ac);
    }

    /**
     * 通过代码指定环境
     */
    @Test
    public void setProfileTest() {
        // 创建一个ApplicationContext
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        // 设置一个需要激活的环境
        ac.getEnvironment().setActiveProfiles("dev", "test");
        // 注册配置类
        ac.register(ProfileConfig.class);
        // 启动刷新容器
        ac.refresh();
        printBeanDefinitionNames(ac);
    }

    private void printBeanDefinitionNames(ApplicationContext ac) {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println("beanDefinitionName.........." + name);
        }
    }
}
