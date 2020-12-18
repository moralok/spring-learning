package com.moralok.controller;

import com.moralok.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author moralok
 * @since 2020/12/16 6:28 下午
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;
}
