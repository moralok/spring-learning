package com.moralok;

import com.moralok.bean.Player;
import com.moralok.config.PropertyValueConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author moralok
 * @since 2020/12/18 2:39 下午
 */
public class PropertyValueTest {

    @Test
    public void test() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PropertyValueConfig.class);
        printBeanDefinitionNames(ac);

        Player player = (Player) ac.getBean("player");
        System.out.println(player);

        ConfigurableEnvironment environment = ac.getEnvironment();
        String property = environment.getProperty("player.nickname");
        System.out.println(property);

        ac.close();
    }

    private void printBeanDefinitionNames(ApplicationContext ac) {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println("beanDefinitionName.........." + name);
        }
    }
}
