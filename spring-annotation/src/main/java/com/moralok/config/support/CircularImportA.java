package com.moralok.config.support;

import org.springframework.context.annotation.Import;

@Import({CircularImportB.class})
public @interface CircularImportA {
}
