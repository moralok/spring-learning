package com.moralok.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Slf4j
public class TestClientWriteTooMuch {

    public static void main(String[] args) {
        try (SocketChannel sc = SocketChannel.open()) {
            sc.connect(new InetSocketAddress("localhost", 18077));

            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            int count = 0;
            while (true) {
                int read = sc.read(buffer);
                // if (read == -1) {
                //     break;
                // }
                count += read;
                log.debug("count: {}", count);
                buffer.clear();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
