package com.moralok.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

@Slf4j
public class TestClientMsgSplit {

    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 18077));
        // sc.write(Charset.defaultCharset().encode("Hello\nWorld\n"));
        sc.write(Charset.defaultCharset().encode("0123456789abcdef00000\n"));
        System.in.read();
    }

}
