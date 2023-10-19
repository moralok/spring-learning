package com.moralok.netty.c2;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TestEventLoop {

    public static void main(String[] args) {
        log.debug("可用处理器数量 {}", NettyRuntime.availableProcessors());
        NioEventLoopGroup group = new NioEventLoopGroup(2);
        log.debug("EventLoop: {}", group.next());
        log.debug("EventLoop: {}", group.next());
        log.debug("EventLoop: {}", group.next());

        // 普通任务
        group.next().submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("test current thread");
        });

        log.debug("main thread");

        // 定时任务
        group.next().scheduleAtFixedRate(() -> {
            log.debug("print every second");
        }, 1, 1, TimeUnit.SECONDS);
    }
}
