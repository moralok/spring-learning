package com.moralok.config;

import com.moralok.service.BookService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * 注解 @ComponentScan
 * value 指定要扫描的包
 * excludeFilters 指定扫描的时候按什么规则排除组件 @Filter[]
 * includeFilters 指定扫描的时候按什么规则包含组件 @Filter[]，必须禁用默认的规则（配置文件中也是如此）
 *
 * 根据测试，先include进来、再exclude？
 *
 * 可重复
 *
 * 说起来，注解的数组形式用{}是什么语法呢
 * 注解@Repeatable
 *
 * @author maowenrou
 * @since 2020/12/16 6:30 下午
 */
@Configuration
@ComponentScan(value = "com.moralok", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
}, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Repository.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookService.class})
}, useDefaultFilters = false)
public class ComponentScanConfig {
}
