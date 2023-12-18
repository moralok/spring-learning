package com.moralok.demo.load.testing.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("while")
@Slf4j
public class WhileController {

    @GetMapping("10ms")
    public String _10ms() {
        long start = System.currentTimeMillis();
        while (true) {
            long now = System.currentTimeMillis();
            if (now - start > 10) {
                break;
            }
        }
        return "10ms";
    }

    @GetMapping("100ms")
    public String _100ms() {
        long start = System.currentTimeMillis();
        while (true) {
            long now = System.currentTimeMillis();
            if (now - start > 100) {
                break;
            }
        }
        return "100ms";
    }
}
