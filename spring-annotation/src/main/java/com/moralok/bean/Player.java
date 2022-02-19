package com.moralok.bean;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author moralok
 * @since 2020/12/18 2:51 下午
 */
public class Player {

    /**
     * 基本类型
     */
    @Value("liudehua")
    private String name;

    /**
     * SpEL表达式
     */
    @Value("#{20-2}")
    private Integer age;

    /**
     * 取出配置文件中的值（严格上说是运行环境中的值，配置文件中的变量也会转化为运行环境中的变量）
     */
    @Value("${player.nickname}")
    private String nickname;

    public Player() {
    }

    public Player(String name, Integer age, String nickname) {
        this.name = name;
        this.age = age;
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
