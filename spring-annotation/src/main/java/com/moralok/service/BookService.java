package com.moralok.service;

import com.moralok.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author moralok
 * @since 2020/12/16 6:29 下午
 */
@Service
public class BookService {

    // @Qualifier("manualBookDao")
    @Autowired(required = false)
    private BookDao bookDao;

    public void print() {
        System.out.println(bookDao);
    }

    @Override
    public String toString() {
        return "BookService{" +
                "bookDao=" + bookDao +
                '}';
    }
}
