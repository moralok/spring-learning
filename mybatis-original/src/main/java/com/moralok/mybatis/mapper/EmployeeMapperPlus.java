package com.moralok.mybatis.mapper;

import com.moralok.mybatis.bean.Employee;

/**
 * @author moralok
 * @since 2021/1/11 2:28 下午
 */
public interface EmployeeMapperPlus {

    /**
     * 查询
     * @param id id
     * @return employee
     */
    Employee getEmployeeById(Integer id);

    /**
     * 查询
     * @param id id
     * @return employee
     */
    Employee getEmployeeAndDeptById(Integer id);

    /**
     * 查询
     * @param id id
     * @return employee
     */
    Employee getEmployeeAndDeptById2(Integer id);

    /**
     * 使用association分步查询
     * @param id id
     * @return employee
     */
    Employee getEmployeeAndDeptStep(Integer id);
}
