package com.moralok.config;

import com.moralok.bean.Player;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 使用@PropertySource读取外部配置文件中的k/v，保存到运行的环境变量中
 * 指定编码防止中文乱码
 *
 * @author moralok
 * @since 2020/12/18 2:37 下午
 */
@PropertySource(value = "classpath:/player.properties", encoding = "UTF-8")
@Configuration
public class PropertyValueConfig {

    @Bean
    public Player player() {
        return new Player();
    }
}
