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

    /**
     * 查询部门和员工
     *
     * @param id id
     * @return 部门
     */
    Department getDeptAndEmployeesById(Integer id);

    /**
     * 查询部门和员工，分步查询
     *
     * @param id id
     * @return 部门
     */
    Department getDeptAndEmployeesByIdStep(Integer id);
}
