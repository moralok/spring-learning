package com.moralok.config;

import com.moralok.bean.Car;
import com.moralok.bean.Manager;
import com.moralok.dao.BookDao;
import com.moralok.dao.ResourceBookDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author moralok
 * @since 2020/12/18 3:29 下午
 */
@Configuration
@ComponentScan(value = {"com.moralok.controller", "com.moralok.service", "com.moralok.dao", "com.moralok.bean"})
public class AutowiredConfig {

    @Bean
    @Primary
    public BookDao manualBookDao() {
        BookDao bookDao = new BookDao();
        bookDao.setLabel(2);
        return bookDao;
    }

    @Bean
    @Primary
    public ResourceBookDao manualResourceBookDao() {
        ResourceBookDao resourceBookDao = new ResourceBookDao();
        resourceBookDao.setLabel(2);
        return resourceBookDao;
    }

    /**
     * 注解 @Bean 标注的方法创建对象时，方法参数的值从容器中获取
     *
     * @param car
     * @return
     */
    @Bean
    public Manager manager(Car car) {
        Manager manager = new Manager();
        manager.setCar(car);
        return manager;
    }
}
