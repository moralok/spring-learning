package com.moralok.servlet.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author moralok
 * @since 2021/1/23
 */
public class BeerExpert {

    public List<String> getBrands(String color) {
        List<String> brands = new ArrayList<>();
        if ("amber".equals(color)) {
            brands.add("Jack Amber");
            brands.add("Red Moose");
        } else {
            brands.add("Jail Pale Ale");
            brands.add("Gout Stout");
        }
        return brands;
    }
}
