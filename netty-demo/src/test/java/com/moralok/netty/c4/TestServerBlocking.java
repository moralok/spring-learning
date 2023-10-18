package com.moralok.netty.c4;

import com.moralok.netty.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TestServerBlocking {

    public static void main(String[] args) {
        try {
            // 创建服务器
            ServerSocketChannel ssc = ServerSocketChannel.open();
            // 绑定端口
            ssc.bind(new InetSocketAddress("localhost", 18077));
            // 连接集合
            List<SocketChannel> channels = new ArrayList<>();
            ByteBuffer buffer = ByteBuffer.allocate(16);
            while (true) {
                log.debug("before connect...{}", ssc);
                SocketChannel sc = ssc.accept();
                log.debug("after connected {}", sc);
                channels.add(sc);
                for (SocketChannel channel : channels) {
                    log.debug("before read...{}", channel);
                    channel.read(buffer);
                    buffer.flip();
                    ByteBufferUtil.debugRead(buffer);
                    buffer.clear();
                    log.debug("after read...{}", channel);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
