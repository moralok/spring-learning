package com.moralok.netty.client;

import com.moralok.netty.message.RpcRequestMessage;
import com.moralok.netty.protocol.MessageCodecSharable;
import com.moralok.netty.protocol.ProtocolFrameDecoder;
import com.moralok.netty.protocol.SequenceIdGenerator;
import com.moralok.netty.server.handler.RpcResponseMessageHandler;
import com.moralok.netty.server.service.HelloService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

@Slf4j
public class RpcClientManager {

    private static volatile Channel channel = null;
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        HelloService helloService = getProxyService(HelloService.class);
        System.out.println(helloService.sayHello("zhangsan"));
        System.out.println(helloService.sayHello("lisi"));
        System.out.println(helloService.sayHello("wangwu"));
    }

    public static Channel getChannel() {
        if (channel != null) {
            return channel;
        }
        synchronized (LOCK) {
            if (channel != null) {
                return channel;
            }
            initChannel();
            return channel;
        }
    }

    private static void initChannel() {
        NioEventLoopGroup group = new NioEventLoopGroup();

        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable messageCodecSharable = new MessageCodecSharable();

        RpcResponseMessageHandler rpcResponseMessageHandler = new RpcResponseMessageHandler();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ProtocolFrameDecoder());
                ch.pipeline().addLast(loggingHandler);
                ch.pipeline().addLast(messageCodecSharable);
                ch.pipeline().addLast(rpcResponseMessageHandler);
            }
        });

        try {
            channel = bootstrap.connect("localhost", 18077).sync().channel();

            channel.closeFuture().addListener(future -> {
                group.shutdownGracefully();
            });
        } catch (InterruptedException e) {
            log.error("server error", e);
        }
    }

    public static <T> T getProxyService(Class<T> serviceClass) {
        ClassLoader loader = serviceClass.getClassLoader();
        Class<?>[] interfaces = new Class<?>[] { serviceClass};
        Object target = Proxy.newProxyInstance(loader, interfaces, (proxy, method, args) -> {
            int sequenceId = SequenceIdGenerator.nextId();
            RpcRequestMessage msg = new RpcRequestMessage(sequenceId,
                    serviceClass.getName(),
                    method.getName(),
                    method.getReturnType(),
                    method.getParameterTypes(),
                    args);
            getChannel().writeAndFlush(msg);

            DefaultPromise<Object> promise = new DefaultPromise<>(getChannel().eventLoop());
            RpcResponseMessageHandler.promiseMap.put(sequenceId, promise);
            // 和 sync 区别是不会抛异常
            promise.await();
            if (promise.isSuccess()) {
                return promise.getNow();
            } else {
                throw new RuntimeException(promise.cause());
            }
        });
        return (T) target;
    }
}
