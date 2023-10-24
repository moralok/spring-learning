package com.moralok.netty.client;

import com.moralok.netty.message.RpcRequestMessage;
import com.moralok.netty.protocol.MessageCodecSharable;
import com.moralok.netty.protocol.ProtocolFrameDecoder;
import com.moralok.netty.server.handler.RpcResponseMessageHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcClient {

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();

        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable messageCodecSharable = new MessageCodecSharable();

        RpcResponseMessageHandler rpcResponseMessageHandler = new RpcResponseMessageHandler();

        try {
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
            Channel channel = bootstrap.connect("localhost", 18077).sync().channel();

            ChannelFuture channelFuture = channel.writeAndFlush(new RpcRequestMessage(1,
                    "com.moralok.netty.server.service.HelloService",
                    "sayHello",
                    String.class,
                    new Class[]{String.class},
                    new Object[]{"zhangsan"}));
            channelFuture.addListener(promise -> {
                if (!promise.isSuccess()) {
                    Throwable cause = promise.cause();
                    log.error("error", cause);
                }
            });

            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error", e);
        } finally {
            group.shutdownGracefully();
        }
    }
}
