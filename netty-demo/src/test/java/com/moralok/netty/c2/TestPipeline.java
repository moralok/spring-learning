package com.moralok.netty.c2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class TestPipeline {

    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                ChannelPipeline pipeline = nioSocketChannel.pipeline();
                                // 默认存在 head 和 tail
                                pipeline.addLast("h1", new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        log.debug("1");
                                        ByteBuf buf = (ByteBuf) msg;
                                        String name = buf.toString(Charset.defaultCharset());
                                        super.channelRead(ctx, name);
                                    }
                                });
                                pipeline.addLast("h2", new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        log.debug("2");
                                        Student student = new Student(msg.toString());
                                        // 必须调用才能调用下一个入站处理器
                                        super.channelRead(ctx, student);
                                    }
                                });

                                // ctx writeAndFlush从当前处理器往前遍历，channel 则是从末尾
                                // pipeline.addLast("test ctx write", new ChannelOutboundHandlerAdapter() {
                                //     @Override
                                //     public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                //         log.debug("test ctx write");
                                //         super.write(ctx, msg, promise);
                                //     }
                                // });

                                pipeline.addLast("h3", new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        log.debug("3 msg: {}, Class: {}", msg, msg.getClass());
                                        super.channelRead(ctx, msg);
                                        nioSocketChannel.writeAndFlush(ctx.alloc().buffer().writeBytes("server return...".getBytes()));
                                        // ctx.writeAndFlush(ctx.alloc().buffer().writeBytes("server return...".getBytes()));
                                    }
                                });

                                // 出站是逆序
                                pipeline.addLast("h4", new ChannelOutboundHandlerAdapter() {
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        log.debug("4");
                                        super.write(ctx, msg, promise);
                                    }
                                });
                                pipeline.addLast("5", new ChannelOutboundHandlerAdapter() {
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        log.debug("5");
                                        super.write(ctx, msg, promise);
                                    }
                                });
                                pipeline.addLast("h6", new ChannelOutboundHandlerAdapter() {
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        log.debug("6");
                                        super.write(ctx, msg, promise);
                                    }
                                });
                            }
                        }
                )
                .bind(18077);
    }

    static class Student {
        String name;

        public Student(String name) {
            this.name = name;
        }
    }
}
