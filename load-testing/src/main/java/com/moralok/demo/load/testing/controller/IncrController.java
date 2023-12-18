package com.moralok.demo.load.testing.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("incr")
@Slf4j
public class IncrController {

    @GetMapping("10mTimes")
    public int incr10mTimes() {
        int count = 10_000_000;
        int sum = 0;
        while (sum <= count) {
            sum++;
        }
        return sum;
    }

    @GetMapping("50mTimes")
    public int incr50mTimes() {
        int count = 50_000_000;
        int sum = 0;
        while (sum <= count) {
            sum++;
        }
        return sum;
    }

    @GetMapping("incrRandom100mTimes")
    public int incr100m() {
        int count = 100_000_000;
        int sum = 0;
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            sum += random.nextInt(10);
        }
        return sum;
    }
}
