package com.moralok.demo.load.testing.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("do")
@Slf4j
public class DoController {

    @GetMapping("/nothing")
    public String nothing() {
        return "nothing";
    }
}
