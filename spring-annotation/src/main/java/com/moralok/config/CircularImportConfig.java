package com.moralok.config;

import com.moralok.config.support.CircularImportA;
import org.springframework.context.annotation.Import;

@Import({CircularImportA.class})
public class CircularImportConfig {
}
