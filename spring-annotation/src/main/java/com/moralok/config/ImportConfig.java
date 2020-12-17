package com.moralok.config;

import com.moralok.bean.Color;
import com.moralok.bean.Red;
import com.moralok.config.support.MyImportBeanDefinitionRegistrar;
import com.moralok.config.support.MyImportSelector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 1. Import
 * 2. ImportSelector
 * 3. ImportBeanDefinitionRegistrar
 *
 * @author moralok
 * @since 2020/12/17
 */
@Configuration
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class ImportConfig {
}
