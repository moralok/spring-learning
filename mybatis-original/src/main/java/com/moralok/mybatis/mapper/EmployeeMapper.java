package com.moralok.mybatis.mapper;

import com.moralok.mybatis.bean.Employee;

/**
 * @author moralok
 * @since 2021/1/8 5:52 下午
 */
public interface EmployeeMapper {

    /**
     * 根据id查询
     *
     * @param id id
     * @return
     */
    Employee getEmployeeById(Integer id);

    /**
     * 添加
     *
     * @param employee
     */
    int addEmployee(Employee employee);

    /**
     * 更新
     *
     * @param employee
     * @return
     */
    int updateEmployee(Employee employee);

    /**
     * 删除
     *
     * @param id
     */
    void deleteEmployeeById(Integer id);
}
