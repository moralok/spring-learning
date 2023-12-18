package com.moralok.demo.load.testing.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Random;

@RestController
@RequestMapping("load-testing")
@Slf4j
public class SortController implements InitializingBean {

    private int[] _20kInts;
    private int[] _50kInts;

    @GetMapping("20k")
    public int sort10m() {
        return sort(_20kInts);
    }

    @GetMapping("50k")
    public int sort50m() {
        return sort(_50kInts);
    }

    private int sort(int[] kInts) {
        int length = kInts.length;
        int[] ret = Arrays.copyOf(kInts, length);
        for (int i = 0; i < length; i++) {
            int min = ret[i];
            for (int j = i; j < length; j++) {
                min = Math.min(min, kInts[j]);
            }
            ret[i] = min;
        }
        return length;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Random random = new Random();

        int _20k = 20_000;
        _20kInts = new int[_20k];
        for (int i = 0; i < _20k; i++) {
            _20kInts[i] = random.nextInt(_20k);
        }

        int _50k = 50_000;
        _50kInts = new int[_50k];
        for (int i = 0; i < _50k; i++) {
            _50kInts[i] = random.nextInt(_50k);
        }
    }
}
