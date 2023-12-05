package com.moralok;

import com.moralok.config.CircularDeferredImportConfig;
import com.moralok.config.CircularImportConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CircularImportTest {

    @Test
    public void circularImport() {
        try {
            ApplicationContext ac = new AnnotationConfigApplicationContext(CircularImportConfig.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void circularDeferredImport() {
        try {
            ApplicationContext ac = new AnnotationConfigApplicationContext(CircularDeferredImportConfig.class);
        } catch (Throwable t) {
            System.out.println(t.getMessage());
        }
    }
}
