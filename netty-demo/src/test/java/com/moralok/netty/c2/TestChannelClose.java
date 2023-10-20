package com.moralok.netty.c2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

@Slf4j
public class TestChannelClose {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(eventExecutors)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                    }
                })
                // 异步非阻塞，连接工作是另一个线程完成的
                .connect(new InetSocketAddress("localhost", 18077));
        // 1. 如果没有 sync，将无阻塞地向下执行，sync 会阻塞等待连接完成
        channelFuture.sync();
        Channel channel = channelFuture.channel();

        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ("q".equals(line)) {
                    // close 也是异步操作，即使放在后面，仍然不能保证关闭后进行处理
                    channel.close();
                    log.debug("试图 channel 关闭后做一些操作2");
                    break;
                }
                channel.writeAndFlush(line);
            }
        }, "input").start();

        // 使用 CloseFuture
        log.debug("试图 channel 关闭后做一些操作1");
        ChannelFuture closeFuture = channel.closeFuture();
        log.debug("waiting close...");
        // closeFuture.sync();
        // log.debug("完成 channel 关闭后地一些操作...real");

        closeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                log.debug("完成 channel 关闭后地一些操作...real");
                eventExecutors.shutdownGracefully();
            }
        });
    }
}
