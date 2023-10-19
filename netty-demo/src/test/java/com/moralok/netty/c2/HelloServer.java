package com.moralok.netty.c2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class HelloServer {

    public static void main(String[] args) {
        // 1. 服务器端的启动器，负责组装我们的 netty 组件
        new ServerBootstrap()
                // 2. 一个 selector + thread 就是一个 EventLoop
                .group(new NioEventLoopGroup())
                // 3. 选择服务器的 ServerSocketChannel 实现
                .channel(NioServerSocketChannel.class)
                // 4. boss 负责处理连接， worker（child） 负责处理读写，决定了 worker 执行什么操作（handler）
                .childHandler(
                        // 5. channel 代表和客户端进行数据读写的通道，Initializer 初始化器，负责添加 handler
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                // 6. 添加具体的 handler，比如字符串解码器，将 ByteBuf 转换成字符串
                                nioSocketChannel.pipeline().addLast(new StringDecoder());
                                // 自定义 handler
                                nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        // 直接打印
                                        System.out.println(msg);
                                    }
                                });
                            }
                        })
                // 7. 绑定监听端口
                .bind(18077);
    }
}
