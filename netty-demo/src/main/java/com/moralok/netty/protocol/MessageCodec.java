package com.moralok.netty.protocol;

import com.moralok.netty.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

@Slf4j
// ByteToMessageCodec 的子类不能被标记为 Sharable
// @ChannelHandler.Sharable
public class MessageCodec extends ByteToMessageCodec<Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        // 1. 4 字节的魔数
        byteBuf.writeBytes(Message.MAGIC);
        // 2. 1 字节的版本
        byteBuf.writeByte(1);
        // 3. 1 字节的序列化方式
        byteBuf.writeByte(0);
        // 4. 1 字节的指令类型
        byteBuf.writeByte(message.getMessageType());
        // 5. 4 字节的序列号
        byteBuf.writeInt(message.getSequenceId());
        // 6. 无意义，对齐填充
        byteBuf.writeByte(0xff);
        // 7. 获取内容的字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(message);
        byte[] bytes = bos.toByteArray();
        // 8. 4 字节的长度
        byteBuf.writeInt(bytes.length);
        // 9. 内容
        byteBuf.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int magicNum = byteBuf.readInt();
        byte version = byteBuf.readByte();
        byte serializeType = byteBuf.readByte();
        byte messageType = byteBuf.readByte();
        int sequenceId = byteBuf.readInt();
        byteBuf.readByte();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes, 0, length);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Message message = (Message) ois.readObject();
        log.debug("{}, {}, {}, {}, {}, {}", magicNum, version, serializeType, messageType, sequenceId, length);
        log.debug("{}", message);
        list.add(message);
    }
}
