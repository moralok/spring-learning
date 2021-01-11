package com.moralok.mybatis.mapper;

import com.moralok.mybatis.bean.Employee;

import java.util.List;

/**
 * @author moralok
 * @since 2021/1/11 4:56 下午
 */
public interface EmployeeMapperDynamicSql {

    /**
     * 查询
     *
     * @param employee 条件
     * @return
     */
    List<Employee> listEmployeeByConditionIf(Employee employee);

    /**
     * 查询
     *
     * @param employee 条件
     * @return
     */
    List<Employee> listEmployeeByConditionTrim(Employee employee);

    /**
     * 查询
     *
     * @param employee 条件
     * @return
     */
    List<Employee> listEmployeeByConditionChoose(Employee employee);

    /**
     * 更新
     *
     * @param employee
     */
    void updateEmployeeWithSet(Employee employee);
}
