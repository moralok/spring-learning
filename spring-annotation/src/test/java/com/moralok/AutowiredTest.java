package com.moralok;

import com.moralok.bean.Boss;
import com.moralok.bean.Car;
import com.moralok.bean.Manager;
import com.moralok.config.AutowiredConfig;
import com.moralok.dao.BookDao;
import com.moralok.dao.ResourceBookDao;
import com.moralok.service.BookService;
import com.moralok.service.ResourceBookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author moralok
 * @since 2020/12/18 3:31 下午
 */
public class AutowiredTest {

    @Test
    public void autowiredTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutowiredConfig.class);
        BookService bookService = ac.getBean(BookService.class);
        System.out.println(bookService);
        BookDao bookDao = (BookDao) ac.getBean("bookDao");
        System.out.println(bookDao);
    }

    @Test
    public void resourceTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutowiredConfig.class);
        ResourceBookService resourceBookService = ac.getBean(ResourceBookService.class);
        System.out.println(resourceBookService);
    }

    @Test
    public void autowiredOtherLocationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutowiredConfig.class);
        Boss boss = ac.getBean(Boss.class);
        System.out.println(boss);
        Car car = ac.getBean(Car.class);
        System.out.println(car);
        ac.close();
    }

    @Test
    public void beanAutowiredTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutowiredConfig.class);
        Manager manager = ac.getBean(Manager.class);
        System.out.println(manager);
        Car car = ac.getBean(Car.class);
        System.out.println(car);
        ac.close();
    }
}
