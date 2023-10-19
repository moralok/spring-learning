package com.moralok.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

@Slf4j
public class TestServerWriteTooMuch {

    public static void main(String[] args) {
        try (ServerSocketChannel ssc = ServerSocketChannel.open();
             Selector selector = Selector.open()) {
            ssc.bind(new InetSocketAddress(18077));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel sc = serverSocketChannel.accept();
                        sc.configureBlocking(false);
                        SelectionKey scKey = sc.register(selector, 0);

                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < 30000000; i++) {
                            sb.append("a");
                        }
                        ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());

                        // while (buffer.hasRemaining()) {
                        //     int write = sc.write(buffer);
                        //     // 当写入太多导致缓冲区满的时候，写入的数量会降为0，这时候线程被阻塞在一个 socket 上
                        //     log.debug("write: {}", write);
                        // }

                        // 改为监听可写事件
                        int write = sc.write(buffer);
                        // 当写入太多导致缓冲区满的时候，写入的数量会降为0，这时候线程被阻塞在一个 socket 上
                        log.debug("write: {}", write);
                        if (buffer.hasRemaining()) {
                            // 避免覆盖原关注事件
                            scKey.interestOps(scKey.interestOps() + SelectionKey.OP_WRITE);
                            // 把 buffer 作为附件
                            scKey.attach(buffer);
                        }
                    } else if (selectionKey.isWritable()) {
                        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                        SocketChannel sc = (SocketChannel) selectionKey.channel();

                        int write = sc.write(buffer);
                        // 当写入太多导致缓冲区满的时候，写入的数量会降为0，这时候线程被阻塞在一个 socket 上
                        log.debug("write: {}", write);

                        // 还要清理 buffers 和关注的写事件
                        if (!buffer.hasRemaining()) {
                            log.debug("writing finished");
                            selectionKey.attach(null);
                            selectionKey.interestOps(selectionKey.interestOps() - SelectionKey.OP_WRITE);
                        }
                    }

                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
