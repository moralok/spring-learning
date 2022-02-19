package com.moralok.config.support;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author moralok
 * @since 2020/12/17
 */
public class MyImportSelector implements ImportSelector {

    /**
     * 返回值就是要导入到容器的组件全类名
     * AnnotationMetadata：当前标注@Import注解类的所有注解信息
     * 这是不是意味着主要还是硬编码字符串，可能会从注解信息获取一些信息用于判断
     *
     * @param annotationMetadata
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[] {"com.moralok.bean.Blue", "com.moralok.bean.Yellow"};
    }
}
