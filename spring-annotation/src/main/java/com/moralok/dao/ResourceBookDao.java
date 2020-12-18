package com.moralok.dao;

import org.springframework.stereotype.Repository;

/**
 * @author moralok
 * @since 2020/12/18 4:10 下午
 */
@Repository
public class ResourceBookDao {

    private Integer label = 1;

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "ResourceBookDao{" +
                "label=" + label +
                '}';
    }
}
