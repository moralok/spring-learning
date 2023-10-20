package com.moralok.netty.c2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.security.pkcs11.wrapper.Constants;

@Slf4j
public class TestByteBuf {

    @Test
    public void testAllocate() {
        // ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        ByteBuf buf = ByteBufAllocator.DEFAULT.heapBuffer();
        // 默认池化直接内存
        log.debug("buffer Class: {}", buf.getClass());
        log(buf);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.append("a");
        }
        buf.writeBytes(sb.toString().getBytes());
        // 容量翻倍了
        log(buf);
    }

    private void log(ByteBuf buffer)  {
        int len = buffer.readableBytes();
        int rows = len / 16 + (len % 15 == 0 ? 0 : 1);
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index:").append(buffer.readerIndex())
                .append(" write index:").append(buffer.writerIndex())
                .append(" capability:").append(buffer.capacity())
                .append(Constants.NEWLINE);
        ByteBufUtil.appendPrettyHexDump(buf, buffer );
        log.debug(buf.toString());
    }

    @Test
    public void testSlice() {
        // 不涉及线程安全吗
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'});
        log(buf);
        // 零拷贝
        ByteBuf slice1 = buf.slice(0, 5);
        ByteBuf slice2 = buf.slice(5, 5);
        log(slice1);
        log(slice2);

        buf.setByte(1, 'z');
        log(buf);
        log(slice1);

        // 报错，不允许超过容量
        // slice1.writeByte('y');

        // 要注意内存释放
        slice1.retain();
        buf.release();
        log(slice1);
        log(slice2);
    }

    @Test
    public void testComposite() {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer();
        buf1.writeBytes(new byte[] {1, 2, 3, 4, 5});
        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer();
        buf2.writeBytes(new byte[] {6, 7, 8, 9, 10});

        // 拷贝数据，性能损失
        // ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        // buffer.writeBytes(buf1).writeBytes(buf2);
        // log(buffer);

        // 零拷贝
        CompositeByteBuf buf = ByteBufAllocator.DEFAULT.compositeBuffer();
        buf.addComponents(true, buf1, buf2);
        log.debug("=====");
        log(buf);
    }
}
