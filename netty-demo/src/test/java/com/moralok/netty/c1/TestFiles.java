package com.moralok.netty.c1;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class TestFiles {

    @Test
    public void testWalkFileTree() {
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        try {
            Files.walkFileTree(Paths.get("C:\\Program Files (x86)\\ZeroTier"), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    log.debug("Dir =====> {}", dir);
                    dirCount.incrementAndGet();
                    return super.preVisitDirectory(dir, attrs);
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    log.debug("File =====> {}", file);
                    fileCount.incrementAndGet();
                    return super.visitFile(file, attrs);
                }
            });
            log.debug("dirCount: {} fileCount: {}", dirCount, fileCount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testWalk() {
        Path source = Paths.get("C:\\Program Files (x86)\\ZeroTier");
        try {
            Files.walk(source).forEach(path -> {
                if (Files.isDirectory(path)) {
                    log.debug("directory: {}", path);
                } else if (Files.isRegularFile(path)) {
                    log.debug("file: {}", path);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
