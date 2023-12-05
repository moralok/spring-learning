package com.moralok.config;

import com.moralok.bean.Red;
import com.moralok.config.support.MyImportBeanDefinitionRegistrar;
import com.moralok.config.support.MyImportSelector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 1. Import（id默认为类的全限定名）
 * 2. ImportSelector
 * 3. ImportBeanDefinitionRegistrar
 * 三者的顺序并不影响 MyImportBeanDefinitionRegistrar 中获取其它两者注入的类的顺序，应该是因为最后处理的原因吧。
 * 如果是通过其它方式后续注入的还是有问题吧？
 *
 * @author moralok
 * @since 2020/12/17
 */
@Configuration
@Import({Red.class, MyImportBeanDefinitionRegistrar.class, MyImportSelector.class,})
public class ImportConfig {
}
