package com.moralok.config;

import com.moralok.bean.AwareBean;
import com.moralok.bean.Car;
import com.moralok.bean.Manager;
import com.moralok.dao.BookDao;
import com.moralok.dao.ResourceBookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    /**
     * 扫描进来一个Dao，这里创建了一个Dao，有两个！！！不要懵逼了
     * 注解@Primary是为自动装配服务的
     *
     * @return
     */
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
     * 注解 @Bean 标注的方法创建对象时，方法参数的值默认从容器中获取
     * 没有魔法？只是可以省略掉@Autowired注解？也可以指定优先级？
     *
     * @param car
     * @return
     */
    @Bean
    public Manager manager(@Qualifier("tesla") Car car) {
        Manager manager = new Manager();
        manager.setCar(car);
        return manager;
    }

    @Bean
    public Car tesla() {
        Car tesla = new Car();
        tesla.setName("tesla");
        return tesla;
    }

    @Bean
    public AwareBean awareBean() {
        return new AwareBean();
    }
}
