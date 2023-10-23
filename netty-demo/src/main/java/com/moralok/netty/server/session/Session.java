package com.moralok.netty.server.session;


import io.netty.channel.Channel;

/**
 * 会话管理接口
 */
public interface Session {

    /**
     * 绑定会话
     * @param channel 要绑定会话的 channel
     * @param username 要绑定会话的用户
     */
    void bind(Channel channel, String username);

    /**
     * 解绑接口
     *
     * @param channel 要解绑会话的 channel
     */
    void unbind(Channel channel);

    /**
     * 获取属性
     *
     * @param channel 从哪一个 channel 获取属性
     * @param name 属性名
     * @return 属性值
     */
    Object getAttribute(Channel channel, String name);

    /**
     * 设置属性
     *
     * @param channel 为哪一个 channel 设置属性
     * @param name 属性名
     * @param value 属性值
     */
    void setAttribute(Channel channel, String name, Object value);

    /**
     * 根据用户名获取 channel
     *
     * @param username 用户名
     * @return channel
     */
    Channel getChannel(String username);
}
