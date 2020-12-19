package com.moralok;

import com.moralok.config.ProfileConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author moralok
 * @since 2020/12/18 6:30 下午
 */
public class ProfileTest {

    @Test
    public void severalTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProfileConfig.class);
        printBeanDefinitionNames(ac);
    }

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
