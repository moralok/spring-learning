package com.moralok.netty.c1;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

@Slf4j
public class TestFileChannel {

    @Test
    public void testTransferTo() {
        try (FileChannel from = new FileInputStream("src/main/resources/data.txt").getChannel();
             FileChannel to = new FileOutputStream("src/main/resources/transferTo.txt").getChannel()) {
            // 效率更高，底层使用零拷贝
            from.transferTo(0, from.size(), to);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testTransferToMoreThan2G() {
        try (FileChannel from = new FileInputStream("C:\\Users\\someone\\Videos\\jellyfin\\media\\movies\\Interstellar (2014) [1080p]\\Interstellar.2014.1080p.BluRay.x264.YIFY.mp4").getChannel();
             FileChannel to = new FileOutputStream("C:\\Users\\someone\\Videos\\jellyfin\\media\\movies\\Interstellar (2014) [1080p]\\Interstellar.2014.1080p.BluRay.x264.YIFY.Copy.mp4").getChannel()) {
            // 每次上限 2g 数据
            long size = from.size();
            long left = size;
            while (left > 0) {
                long count = from.transferTo(size - left, left, to);
                log.debug("count: {}", count);
                left -= count;
            }
            from.transferTo(0, from.size(), to);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
