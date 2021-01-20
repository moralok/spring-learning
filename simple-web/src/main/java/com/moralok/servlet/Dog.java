package com.moralok.servlet;

/**
 * @author moralok
 * @since 2021/1/20 2:20 下午
 */
public class Dog {

    private String breed;

    public Dog(String breed) {
        this.breed = breed;
        System.out.println("Dog constructed, breed is: " + breed);
    }

    public String getBreed() {
        return breed;
    }
}
