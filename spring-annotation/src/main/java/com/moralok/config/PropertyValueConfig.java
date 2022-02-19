package com.moralok.config;

import com.moralok.bean.Player;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 使用@PropertySource读取外部配置文件中的k/v，保存到运行的环境变量中（例如xxx.properties，肯定是在某个地方引入了文件的，没有"魔法"）
 * 指定编码防止中文乱码
 * 注解@Value的效果等同于配置文件中property标签中的value属性
 *
 * 实现原理的"魔法"初步猜测，引入配置文件后被转换到运行环境中，@Value就从运行环境中取值
 * 重复的话规则如何呢？经测试为覆盖
 *
 * afterPropertiesSet包含属性赋值和依赖注入吗
 *
 * @author moralok
 * @since 2020/12/18 2:37 下午
 */
@PropertySource(value = "classpath:/player.properties", encoding = "UTF-8")
@PropertySource(value = "classpath:/player-duplication.properties", encoding = "UTF-8")
@Configuration
public class PropertyValueConfig {

    @Bean
    public Player player() {
        return new Player();
    }
}
