package com.moralok.netty.c3;

import com.moralok.netty.config.Config;
import com.moralok.netty.message.LoginRequestMessage;
import com.moralok.netty.message.Message;
import com.moralok.netty.protocol.MessageCodecSharable;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.Test;

public class TestSerializer {

    @Test
    public void testSerialize() {
        MessageCodecSharable codec = new MessageCodecSharable();
        LoggingHandler loggingHandler = new LoggingHandler();
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(loggingHandler, codec, loggingHandler);
        embeddedChannel.writeOutbound(new LoginRequestMessage("zhangsan", "123"));
    }

    @Test
    public void testDeserialize() {
        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123");
        ByteBuf buf = objectToByteBuf(message);
        MessageCodecSharable codec = new MessageCodecSharable();
        LoggingHandler loggingHandler = new LoggingHandler();
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(loggingHandler, codec, loggingHandler);
        embeddedChannel.writeInbound(buf);
    }

    private ByteBuf objectToByteBuf(Message message) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeBytes(Message.MAGIC);
        byteBuf.writeByte(1);
        byteBuf.writeByte(Config.getSerializerAlgorithm().ordinal());
        byteBuf.writeByte(message.getMessageType());
        byteBuf.writeInt(message.getSequenceId());
        byteBuf.writeByte(0xff);
        byte[] bytes = Config.getSerializerAlgorithm().serialize(message);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
