package com.moralok.config.support;

import com.moralok.bean.Color;
import org.springframework.beans.factory.FactoryBean;

/**
 * 创建一个Spring定义的FactoryBean
 *
 * @author moralok
 * @since 2020/12/17
 */
public class ColorFactoryBean implements FactoryBean<Color> {

    /**
     * 返回一个Color对象，对象会添加到容器中
     *
     * @return
     * @throws Exception
     */
    @Override
    public Color getObject() throws Exception {
        System.out.println("ColorFactoryBean.getObject...被调用");
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    /**
     * 控制是否单实例
     * true：保存一份
     * false：每次获取都创建
     *
     * @return
     */
    @Override
    public boolean isSingleton() {
        return false;
    }
}
