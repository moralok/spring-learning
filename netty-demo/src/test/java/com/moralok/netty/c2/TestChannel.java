package com.moralok.netty.c2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class TestChannel {

    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                    }
                })
                // 异步非阻塞，连接工作是另一个线程完成的
                .connect(new InetSocketAddress("localhost", 18077));
        // 1. 如果没有 sync，将无阻塞地向下执行，sync 会阻塞等待连接完成
        // channelFuture.sync();
        // Channel channel = channelFuture.channel();
        // log.debug("channel 信息: {}", channel);
        // channel.writeAndFlush("must sync first");
        // System.out.println(channel);

        // 2 使用 addListener 回调对象方法异步处理结果
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Channel channel = channelFuture.channel();
                channel.writeAndFlush("use addListener");
            }
        });
    }
}
