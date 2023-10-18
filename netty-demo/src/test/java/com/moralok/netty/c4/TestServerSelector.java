
package com.moralok.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

@Slf4j
public class TestServerSelector {

    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();
            // 创建服务器
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            // 绑定端口
            ssc.bind(new InetSocketAddress("localhost", 18077));
            SelectionKey sscKey = ssc.register(selector, 0, null);
            log.debug("sscKey: {}", sscKey);
            sscKey.interestOps(SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();
                log.debug("select 返回");
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    log.debug("selectionKey: {}", key);
                    // 区分事件类型
                    if (key.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        SocketChannel sc = channel.accept();
                        sc.configureBlocking(false);
                        SelectionKey scKey = sc.register(selector, 0, null);
                        scKey.interestOps(SelectionKey.OP_READ);
                        log.debug("after connected {}", sc);
                    } else if (key.isReadable()) {
                        try {
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(4);
                            int read = channel.read(buffer);
                            if (read == -1) {
                                // 正常断开必须使用 cancel 才能有效移除？
                                key.cancel();
                            } else {
                                buffer.flip();
                                // ByteBufferUtil.debugRead(buffer);
                                // 当 buffer 太小导致读取错误，编码边界错误
                                // 半包、粘包现象
                                // 定长、分隔符、TLV
                                log.debug("msg: {}", Charset.defaultCharset().decode(buffer));
                            }
                        } catch (IOException e) {
                            // 客户端断开会触发一个读事件，不处理的话会陷入循环
                            key.cancel();
                            e.printStackTrace();
                        }
                    }
                    // 不删除的话，下次会再次拿到，在非阻塞模式下，下次返回的 sc 为 null
                    // 没处理的话，select 不会阻塞，处理了没删，select 会阻塞，但是还会有问题
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
