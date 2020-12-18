package com.moralok.bean;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author moralok
 * @since 2020/12/16 5:40 下午
 */
public class Person {

    @Value("liudehua")
    private String name;

    @Value("#{20-2}")
    private Integer age;

    public Person() {
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
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

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
