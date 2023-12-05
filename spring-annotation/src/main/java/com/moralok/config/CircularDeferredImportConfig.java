package com.moralok.config;

import com.moralok.config.support.CircularDeferredImportSelectorA;
import org.springframework.context.annotation.Import;

@Import({CircularDeferredImportSelectorA.class})
public class CircularDeferredImportConfig {
}
