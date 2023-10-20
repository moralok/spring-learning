package com.moralok.netty.c2;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

@Slf4j
public class TestFuture {

    @Test
    public void testJdkFuture() throws ExecutionException, InterruptedException {
        // 线程池
        ExecutorService service = Executors.newFixedThreadPool(2);
        // 提交任务
        Future<Integer> future = service.submit(() -> {
            log.debug("计算结果");
            TimeUnit.SECONDS.sleep(5);
            return 111;
        });

        // 主线程获取结果
        log.debug("等待结果");
        log.debug("结果是 {}", future.get());
    }

    // 这一类不能用 Junit 测试
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop eventLoop = group.next();
        io.netty.util.concurrent.Future<Integer> future = eventLoop.submit(() -> {
            log.debug("计算结果");
            TimeUnit.SECONDS.sleep(5);
            return 111;
        });

        log.debug("等待结果 {}", future.getNow());
        // log.debug("结果是 {}", future.get());
        future.addListener(new GenericFutureListener<io.netty.util.concurrent.Future<? super Integer>>() {
            @Override
            public void operationComplete(io.netty.util.concurrent.Future<? super Integer> future) throws Exception {
                log.debug("结果是 {}", future.getNow());
            }
        });
    }
}
