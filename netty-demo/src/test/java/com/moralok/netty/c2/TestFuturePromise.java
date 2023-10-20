package com.moralok.netty.c2;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestFuturePromise {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1. 准备 EventLoop 对象
        EventLoop eventLoop = new NioEventLoopGroup().next();
        // 2. 主动创建 Promise
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        new Thread(() -> {
            // 填充结果
            log.debug("开始计算");
            try {
                int i = 1 / 0;
                TimeUnit.SECONDS.sleep(5);
                promise.setSuccess(100);
            } catch (Exception e) {
                e.printStackTrace();
                promise.setFailure(e);
            }
            promise.setSuccess(100);
        }).start();

        log.debug("等待结果");
        // 接受结果
        log.debug("结果 {}", promise.get());
    }
}
