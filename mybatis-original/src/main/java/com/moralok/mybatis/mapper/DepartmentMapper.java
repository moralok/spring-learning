package com.moralok.mybatis.mapper;

import com.moralok.mybatis.bean.Department;

/**
 * @author moralok
 * @since 2021/1/11 3:05 下午
 */
public interface DepartmentMapper {

    /**
     * 查询
     * @param id id
     * @return department
     */
    Department getDeptById(Integer id);
}
