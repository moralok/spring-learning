package com.moralok.netty.server.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        // int i = 1 / 0;
        return "hello " + name;
    }
}
