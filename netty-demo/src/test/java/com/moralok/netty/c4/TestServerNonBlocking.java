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
public class TestServerNonBlocking {

    public static void main(String[] args) {
        try {
            // 创建服务器
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            // 绑定端口
            ssc.bind(new InetSocketAddress("localhost", 18077));
            // 连接集合
            List<SocketChannel> channels = new ArrayList<>();
            ByteBuffer buffer = ByteBuffer.allocate(16);
            while (true) {
                SocketChannel sc = ssc.accept();
                if (sc != null) {
                    log.debug("after connected {}", sc);
                    sc.configureBlocking(false);
                    channels.add(sc);
                }
                for (SocketChannel channel : channels) {
                    int read = channel.read(buffer);
                    if (read > 0) {
                        buffer.flip();
                        ByteBufferUtil.debugRead(buffer);
                        buffer.clear();
                        log.debug("after read...{}", channel);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
