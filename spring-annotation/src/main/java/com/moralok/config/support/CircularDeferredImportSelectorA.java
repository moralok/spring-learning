package com.moralok.config.support;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class CircularDeferredImportSelectorA implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {CircularDeferredImportSelectorB.class.getName()};
    }
}
