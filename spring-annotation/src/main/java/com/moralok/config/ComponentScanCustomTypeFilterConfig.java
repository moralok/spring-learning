package com.moralok.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @author moralok
 * @since 2020/12/17 9:43 上午
 */
@Configuration
@ComponentScan(value = "com.moralok", includeFilters = {
        @ComponentScan.Filter(type = FilterType.CUSTOM, value = MyTypeFilter.class)
}, useDefaultFilters = false)
public class ComponentScanCustomTypeFilterConfig {
}
