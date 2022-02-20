package com.moralok.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * @author moralok
 * @since 2020/12/16 6:29 下午
 */
@Repository
public class BookDao {

    private Integer label = 1;

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "BookDao{" +
                "label=" + label +
                '}';
    }
}
