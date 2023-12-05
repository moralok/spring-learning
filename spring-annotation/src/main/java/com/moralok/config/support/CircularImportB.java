package com.moralok.config.support;

import org.springframework.context.annotation.Import;

@Import({CircularImportA.class})
public @interface CircularImportB {
}
