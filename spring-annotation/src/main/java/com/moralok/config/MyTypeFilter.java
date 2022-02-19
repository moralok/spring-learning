package com.moralok.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * MetadataReader：读取到的当前正在扫描的类的信息
 * MetadataReaderFactory：可以获取其他任何类的信息
 * 即使没有标注@Controller之类的注解，若满足match方法，也会被注册到容器中，比如MyTypeFilter自身
 *
 * @author moralok
 * @since 2020/12/17 9:37 上午
 */
public class MyTypeFilter implements TypeFilter {
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        // 获取当前类注解的信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        // 获取当前类的信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        // 获取当前类资源（普通类的资源是什么？class 文件）
        Resource resource = metadataReader.getResource();

        String className = classMetadata.getClassName();
        System.out.println("classMetadata.........." + className + "  result: " + className.contains("Red"));
        MetadataReader anotherMetadataReader = metadataReaderFactory.getMetadataReader("com.moralok.config.MyTypeFilter");
        String anotherClassName = anotherMetadataReader.getClassMetadata().getClassName();
        System.out.println("annotationMetadata.........." + anotherClassName);
        return className.contains("Red");
    }
}
