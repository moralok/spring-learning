package com.moralok.mybatis.mapper;

import com.moralok.mybatis.bean.Employee;

/**
 * @author moralok
 * @since 2021/1/11 2:28 下午
 */
public interface EmployeeMapperPlus {

    /**
     * 查询
     * @param id
     * @return
     */
    Employee getEmployeeById(Integer id);
}
