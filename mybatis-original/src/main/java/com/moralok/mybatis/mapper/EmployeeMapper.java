package com.moralok.mybatis.mapper;

import com.moralok.mybatis.Employee;

/**
 * @author moralok
 * @since 2021/1/8 5:52 下午
 */
public interface EmployeeMapper {

    Employee getEmployeeById(Integer id);
}
