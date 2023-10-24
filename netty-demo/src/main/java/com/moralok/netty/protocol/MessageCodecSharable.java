package com.moralok.netty.protocol;

import com.moralok.netty.config.Config;
import com.moralok.netty.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> list) throws Exception {
        ByteBuf byteBuf = channelHandlerContext.alloc().buffer();
        // 1. 4 字节的魔数
        byteBuf.writeBytes(Message.MAGIC);
        // 2. 1 字节的版本
        byteBuf.writeByte(1);
        // 3. 1 字节的序列化方式
        byteBuf.writeByte(Config.getSerializerAlgorithm().ordinal());
        // 4. 1 字节的指令类型
        byteBuf.writeByte(message.getMessageType());
        // 5. 4 字节的序列号
        byteBuf.writeInt(message.getSequenceId());
        // 6. 无意义，对齐填充
        byteBuf.writeByte(0xff);
        // 7. 获取内容的字节数组
        byte[] bytes = Config.getSerializerAlgorithm().serialize(message);
        // 8. 4 字节的长度
        byteBuf.writeInt(bytes.length);
        // 9. 内容
        byteBuf.writeBytes(bytes);
        list.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int magicNum = byteBuf.readInt();
        byte version = byteBuf.readByte();
        byte serializeAlgorithm = byteBuf.readByte();
        byte messageType = byteBuf.readByte();
        int sequenceId = byteBuf.readInt();
        byteBuf.readByte();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes, 0, length);
        Serializer.Algorithm algorithm = Serializer.Algorithm.values()[serializeAlgorithm];
        Message message = (Message) algorithm.deserialize(Message.getMessageClass(messageType), bytes);
        log.debug("{}, {}, {}, {}, {}, {}", magicNum, version, serializeAlgorithm, messageType, sequenceId, length);
        log.debug("{}", message);
        list.add(message);
    }
}
