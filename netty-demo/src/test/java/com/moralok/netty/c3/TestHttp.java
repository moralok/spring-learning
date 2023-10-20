package com.moralok.netty.c3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;

@Slf4j
public class TestHttp {

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new LoggingHandler());
                    socketChannel.pipeline().addLast(new HttpServerCodec());

                    // 可以直接指定感兴趣的消息类型
                    socketChannel.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest) throws Exception {
                            log.debug("msg uri: {}", httpRequest.uri());
                            // 返回响应
                            DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                                    httpRequest.protocolVersion(),
                                    HttpResponseStatus.OK);
                            byte[] bytes = "<h1>Hello, world!<h1>".getBytes();
                            response.headers().setInt(CONTENT_LENGTH, bytes.length);
                            response.content().writeBytes(bytes);
                            channelHandlerContext.writeAndFlush(response);
                        }
                    });

                    socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.debug("msg: {}", msg.getClass());
                            
                            if (msg instanceof HttpRequest) {
                                HttpRequest request = (HttpRequest) msg;
                                log.debug("msg uri: {}", request.uri());
                            } else if (msg instanceof HttpContent) {
                                log.debug("hello");
                            }
                        }
                    });
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(18077).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
