package com.moralok.netty.c3;

import com.moralok.netty.message.LoginRequestMessage;
import com.moralok.netty.message.Message;
import com.moralok.netty.protocol.MessageCodec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestMessageCodec {

    @Test
    public void testEncode() {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                new LoggingHandler(LogLevel.DEBUG),
                new MessageCodec());
        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123");
        embeddedChannel.writeOutbound(message);
    }

    @Test
    public void testDecode() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 无状态的 handler 可以多线程共享 @Sharable
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                loggingHandler,
                new LengthFieldBasedFrameDecoder(1024, 12, 4, 0, 0),
                new MessageCodec());
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123");
        MessageCodec messageCodec = new MessageCodec();
        Method method = MessageCodec.class.getDeclaredMethod("encode", ChannelHandlerContext.class, Message.class, ByteBuf.class);
        method.setAccessible(true);
        method.invoke(messageCodec, null, message, buf);
        // embeddedChannel.writeInbound(buf);

        // 测试半包现象
        ByteBuf slice1 = buf.slice(0, 100);
        ByteBuf slice2 = buf.slice(100, buf.readableBytes() - 100);
        slice1.retain();
        embeddedChannel.writeInbound(slice1);
        embeddedChannel.writeInbound(slice2);
    }
}
