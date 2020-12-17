package com.moralok.config;

import com.moralok.bean.Color;
import com.moralok.bean.Red;
import com.moralok.selector.MyImportSelector;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author moralok
 * @since 2020/12/17
 */
@Configuration
@Import({Color.class, Red.class, MyImportSelector.class})
public class ImportConfig {
}
