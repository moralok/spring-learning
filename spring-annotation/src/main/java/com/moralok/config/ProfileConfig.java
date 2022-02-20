package com.moralok.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @author moralok
 * @since 2020/12/18 6:10 下午
 */
@Configuration
@PropertySource("classpath:/db.properties")
public class ProfileConfig implements EmbeddedValueResolverAware {

    /**
     * 直接获取配置文件
     */
    @Value("${db.user}")
    private String user;

    private StringValueResolver resolver;

    private String driverClass;

    @Profile("dev")
    @Bean
    public DataSource dataSourceDev(@Value("${db.password}") String password) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/");
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    /**
     * 参数位置使用@Value
     * @param password
     * @return
     * @throws PropertyVetoException
     */
    @Profile("test")
    @Bean
    public DataSource dataSourceTest(@Value("${db.password}") String password) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/");
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    @Profile("prod")
    @Bean
    public DataSource dataSourceProd(@Value("${db.password}") String password) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/");
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    /**
     * 手动解析字符串
     * @param resolver
     */
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.resolver = resolver;
        driverClass = resolver.resolveStringValue("${db.driver}");
    }
}
