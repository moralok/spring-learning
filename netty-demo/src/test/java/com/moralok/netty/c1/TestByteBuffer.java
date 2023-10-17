package com.moralok.netty.c1;

import com.moralok.netty.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class TestByteBuffer {

    @Test
    public void testFileChannel() {
        try (FileChannel channel = new FileInputStream("src/main/resources/data.txt").getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true) {
                int len = channel.read(buffer);
                log.debug("读取到的字节数 {}", len);
                if (len == -1) {
                    break;
                }
                buffer.flip();
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    log.debug("读取到的字符 {}", (char) b);
                }
                buffer.clear();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        // 向buffer中写入1个字节的数据
        buffer.put((byte)97);
        // 使用工具类，查看buffer状态
        ByteBufferUtil.debugAll(buffer);

        // 向buffer中写入4个字节的数据
        buffer.put(new byte[]{98, 99, 100, 101});
        ByteBufferUtil.debugAll(buffer);

        // 获取数据
        buffer.flip();
        ByteBufferUtil.debugAll(buffer);
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        ByteBufferUtil.debugAll(buffer);

        // 使用compact切换模式
        buffer.compact();
        ByteBufferUtil.debugAll(buffer);

        // 再次写入
        buffer.put((byte)102);
        buffer.put((byte)103);
        ByteBufferUtil.debugAll(buffer);
    }

    @Test
    public void testByteBufferAllocate() {
        log.debug("allocate: {}", ByteBuffer.allocate(16).getClass());
        log.debug("allocate: {}", ByteBuffer.allocateDirect(16).getClass());
    }

    @Test
    public void testRewind() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});
        buffer.flip();
        byte[] bytes = new byte[4];
        buffer.get(bytes);
        log.debug("bytes: {}", bytes);
        ByteBufferUtil.debugAll(buffer);
        buffer.rewind();
        log.debug("char: {}", buffer.get());
    }

    @Test
    public void testMarkAndReset() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});
        buffer.flip();
        ByteBufferUtil.debugAll(buffer);
        buffer.get();
        buffer.get();
        ByteBufferUtil.debugAll(buffer);
        buffer.mark();
        buffer.get();
        buffer.get();
        buffer.reset();
        ByteBufferUtil.debugAll(buffer);
    }

    @Test
    public void testGet() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});
        buffer.flip();
        ByteBufferUtil.debugAll(buffer);
        buffer.get(3);
        ByteBufferUtil.debugAll(buffer);
    }

    @Test
    public void testStringToByteBuffer1() {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("hello".getBytes());
        ByteBufferUtil.debugAll(buffer);
    }

    @Test
    public void testStringToByteBuffer2() {
        ByteBuffer buffer = StandardCharsets.UTF_8.encode("hello");
        ByteBufferUtil.debugAll(buffer);
    }

    @Test
    public void testStringToByteBuffer3() {
        ByteBuffer buffer = Charset.forName("utf-8").encode("hello");
        ByteBufferUtil.debugAll(buffer);
    }

    @Test
    public void testStringToByteBuffer4() {
        ByteBuffer buffer = ByteBuffer.wrap("hello".getBytes());
        ByteBufferUtil.debugAll(buffer);
    }

    @Test
    public void testByteBufferToString1() {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("hello".getBytes());
        CharBuffer decode = StandardCharsets.UTF_8.decode(buffer);
        log.debug("decode: {}", decode);
        buffer.flip();
        decode = StandardCharsets.UTF_8.decode(buffer);
        ByteBufferUtil.debugAll(buffer);
        log.debug("decode: {}", decode);
    }

    @Test
    public void testByteBufferToString2() {
        ByteBuffer buffer = StandardCharsets.UTF_8.encode("hello");
        CharBuffer decode = StandardCharsets.UTF_8.decode(buffer);
        ByteBufferUtil.debugAll(buffer);
        log.debug("decode: {}", decode);
    }

    @Test
    public void testScatteringRead() {
        try (FileChannel channel = new RandomAccessFile("src/main/resources/ScatteringRead.txt", "r").getChannel()) {
            ByteBuffer b1 = ByteBuffer.allocate(3);
            ByteBuffer b2 = ByteBuffer.allocate(3);
            ByteBuffer b3 = ByteBuffer.allocate(5);
            channel.read(new ByteBuffer[]{b1, b2, b3});
            ByteBufferUtil.debugAll(b1);
            ByteBufferUtil.debugAll(b2);
            ByteBufferUtil.debugAll(b3);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGatheringWrite() {
        ByteBuffer b1 = StandardCharsets.UTF_8.encode("hello");
        ByteBuffer b2 = StandardCharsets.UTF_8.encode("world");
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("你好");

        try (FileChannel channel = new RandomAccessFile("src/main/resources/GatheringWrite.txt", "rw").getChannel()) {
            channel.write(new ByteBuffer[]{b1, b2, b3});
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
