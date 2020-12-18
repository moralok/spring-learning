package com.moralok;

import com.moralok.config.AutowiredConfig;
import com.moralok.dao.BookDao;
import com.moralok.service.BookService;
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
}
